<template class="bg-white">
  <div class="bg-white p-4 shadow-lg rounded-lg">
    <div class="grid lg:grid-cols-2 gap-3">
      <div> <!-- barcode scan or type-->
        <el-form :inline="true">
          <el-form-item label="Place" prop="place" label-width="120px">
            <el-select v-model="placeId" placeholder="Select" filterable>
              <el-option
                v-for="placeItems in placeList"
                :key="placeItems.id"
                :label="placeItems.placeName"
                :value="placeItems.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Code"  prop="Code" label-width="120px">
            <el-input v-model="productCode" autocomplete="off"></el-input>
          </el-form-item>
          <!--<el-form-item>
            <el-button type="primary" @click="submitProductCode()">Add</el-button>
          </el-form-item>-->
        </el-form>
        <div>
          <el-form :model="productDetail" class="grid lg:grid-cols-2 gap-3 px-2">
            <el-form-item label="Qty"  prop="qty" label-width="120px">
              <el-input-number v-model="productDetail.qty" :step="1"></el-input-number>
            </el-form-item>
            <el-form-item label="Retail Price"  prop="retailPrice" label-width="120px">
              <el-input v-model="productDetail.retailPrice" readonly  class="w-full"></el-input>
            </el-form-item>
            
            <div class="grid grid-cols-6 gap-3 lg:col-span-full">
              <el-form-item label="Discount"  prop="discount" label-width="120px" class="col-span-4">
                <el-input-number v-model="productDetail.discount" :step="1" class="w-full" />
              </el-form-item>
              <el-select v-model="productDetail.discountType" placeholder="Select" filterable :disabled="!productDetail.discount">
                  <el-option
                    v-for="discount in discountList"
                    :key="discount.type"
                    :label="discount.type"
                    :value="discount.type">
                  </el-option>
              </el-select>
              <el-form-item>
                <el-button class="w-full" type="primary" @click="refreshSumTotalPrice()">Refresh</el-button>
              </el-form-item>

              
            </div>
            <el-form-item label="Total"  prop="total" label-width="120px">
              <el-input v-model="productDetail.totalPriceAll" readonly></el-input>
            </el-form-item>
            <el-form-item>
              <el-button v-if="productDetail.productCode" type="primary" @click="submitPreSell()">Add</el-button>
            </el-form-item>

          </el-form>
        </div>
      </div>

      <div class="px-2 gap-3">
        <!-- List Product -->
        <el-table 
          ref="multipleTable"
          :data="preSellList"
          tooltip-effect="dark"
          @row-click="changeSellProduct"
          @selection-change="handleSelectionChange">
            <el-table-column
              prop="productCode"
              label="Product Code">
            </el-table-column>
            <el-table-column
              prop="qty"
              label="Qty">
            </el-table-column>
            <el-table-column
              prop="retailPrice"
              label="Retail Price">
            </el-table-column>
             <el-table-column
              prop="discountType"
              label="Discount Type">
            </el-table-column>
            <el-table-column
              prop="discount"
              label="Discount">
            </el-table-column>
            <el-table-column
              prop="totalPrice"
              label="Total Price">
            </el-table-column>
            <el-table-column
              prop="description"
              label="Description">
            </el-table-column>
            <el-table-column
              prop="taxAmount"
              label="Tax Amount">
            </el-table-column>
            <el-table-column
              prop="afterTax"
              label="After Tax">
            </el-table-column>
        </el-table>
        <el-form :inline="true" class="py-6">
          <el-form-item label="Member"  prop="name" label-width="130px">
            <el-input v-model="memberNameFind" autocomplete="off"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="findMember()">Find</el-button>
          </el-form-item>
        </el-form>

        <el-table
          ref="multipleTable"
          :data="foundMemberList"
          tooltip-effect="dark"
          @row-click="selectMember"
          @selection-change="handleSelectionChange">
           <el-table-column
              prop="name"
              label="Name">
            </el-table-column>
            <el-table-column
              prop="phone"
              label="Phone">
            </el-table-column>
            <el-table-column
              prop="email"
              label="Email">
            </el-table-column>
          </el-table>
          <el-form :model="selectedMember" :disabled="true" class="p-3">
            <el-form-item label="Name"  prop="namr" label-width="130px">
              <el-input v-model="selectedMember.name"></el-input>
            </el-form-item>
            <el-form-item label="Phone"  prop="retailPrice" label-width="130px">
              <el-input v-model="selectedMember.phone"></el-input>
            </el-form-item>
            <el-form-item label="Member Class" prop="classes" label-width="130px">
            <el-select v-model="selectedMember.classes" placeholder="Select">
              <el-option
                v-for="item in allClasses"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-form :model="totalCalForm" class="p-3">
          <el-form-item label="Before Discount Total"  prop="total" label-width="130px">
            <el-input v-model="totalCalForm.totalCal" readonly></el-input>
          </el-form-item>
          <div class="grid grid-cols-6 gap-3">
            <el-form-item label="Discount"  prop="discount" label-width="130px" class="col-span-4">
              <el-input-number v-model="totalCalForm.discount" :step="1" class="w-full" ></el-input-number>
            </el-form-item>
            <el-select v-model="totalCalForm.discountType" placeholder="Select" filterable :disabled="!totalCalForm.discount">
              <el-option
                v-for="discount in discountList"
                :key="discount.type"
                :label="discount.type"
                :value="discount.type">
              </el-option>
            </el-select>
            <el-form-item>
              <el-button class="w-full" type="primary" @click="refershNewTotal()">Refresh</el-button>
            </el-form-item>
          </div>

          <el-form-item label="Total"  prop="total" label-width="130px">
            <el-input-number v-model="totalCalForm.totalPriceAll" readonly></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="cancelPO()">Cancel</el-button>
            <el-button type="primary" @click="payPO()">Pay</el-button>
          </el-form-item>
        </el-form>

      </div>

      

    </div>

    <el-dialog
      class="rounded-lg"
      title="Pay"
      :visible.sync="dialogVisible"
      width="1000px"
      :before-close="handleClose">
      <el-form :model="payForm">
        <el-form-item label="Total"  prop="total" label-width="130px">
          <el-input v-model="payForm.totalPrice" readonly></el-input>
        </el-form-item>
        <br>
        <h2>Pay Method</h2>
        <br>
        <el-row :span="24">
          <el-col :span="6">
            <el-select v-model="payFormMethod.payMethod" placeholder="Select" filterable>
                <el-option
                  v-for="payMethod in payMethodCategory"
                  :key="payMethod.key"
                  :label="payMethod.key"
                  :value="payMethod.key">
                </el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-input-number v-model="payFormMethod.amount" />
          </el-col>
          <el-col :span="6">
            <el-button type="primary" @click="paymentAction()">Pay</el-button>
          </el-col>
        </el-row>
        <el-divider direction="horizontal"></el-divider>
        <br>
        <el-table
          ref="multipleTable"
          :data="payList"
          tooltip-effect="dark">
          <el-table-column
          prop="method"
          label="Method">
          </el-table-column>
          <el-table-column
          prop="amount"
          label="Amount">
          </el-table-column>
        </el-table>
        <br>
        <el-form-item label="Balance"  prop="balance" label-width="130px">
          <el-input v-model="payForm.balance" readonly></el-input>
        </el-form-item>
        <el-form-item label="Find Redemption"  prop="findRedemption" label-width="130px">
          <el-input v-model="payForm.findRedemption" readonly></el-input>
        </el-form-item>
         <el-form-item>
            <el-button type="primary" @click="submitMemberPayment()">Confirm</el-button>
            <el-button type="primary" @click="CancelMemberPayment()">Cancel</el-button>
          </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import moment from 'moment'
import { Component, Vue, Watch } from 'vue-property-decorator'

@Component
export default class POSpage extends Vue {
  discountList = [
    { type: '%' },
    { type: '$' },
  ]

  payMethodCategory: any = [
    { key: 'Cash' },
    { key: 'IC Card' },
    { key: 'Credit Card' },
    { key: 'E-payment' },
  ]

  handleSelectionChange: boolean = false


  placeList: any = []

  productCode: string = ''
  placeId: number = 0
  discount: number = 0
  totalPrice: number = 0

  productDetail: any = {
    qty: 1
  }

  preSellList: any =[]

  memberNameFind: string = ''
  foundMemberList: any = []
  selectedMember: any = {}
  allClasses: any = []

  totalCalForm: any = {
    totalCal: 0
  }
  dialogVisible: boolean = false
   handleClose: boolean = false
  payForm: any = {}

  payList: any = []

  payFormMethod: any = {}

  @Watch('productDetail.discount', { immediate: true, deep: true })
  sumTotalPrice() {
    if (this.productDetail.discountType === '%') {
      this.productDetail.totalPriceAll = ( this.productDetail.retailPrice* (1-(this.productDetail.discount/100)) ) * this.productDetail.qty * (1+this.productDetail.taxRate)
    } else if (this.productDetail.discountType === '$') {
      this.productDetail.totalPriceAll= ( this.productDetail.retailPrice - this.productDetail.discount ) * this.productDetail.qty * (1+this.productDetail.taxRate)
    } else {
      this.productDetail.totalPriceAll = this.productDetail.totalPrice
    }
  }

  refreshSumTotalPrice() {
    if (this.productDetail.discountType === '%') {
      this.productDetail.totalPriceAll = ( this.productDetail.retailPrice* (1-(this.productDetail.discount/100)) ) * this.productDetail.qty * (1+this.productDetail.taxRate)
    } else if (this.productDetail.discountType === '$') {
      this.productDetail.totalPriceAll= ( this.productDetail.retailPrice - this.productDetail.discount ) * this.productDetail.qty * (1+this.productDetail.taxRate)
    } else {
      this.productDetail.totalPriceAll = this.productDetail.totalPrice
    }
  }

  @Watch('productDetail.qty', { immediate: true, deep: true }) 
  countAmount() {
    this.productDetail.totalPrice = this.productDetail.retailPrice * this.productDetail.qty
  }

 

  created() {
    this.getAllPlace()
    this.getAllClass()
    this.getUserInfo()
  }

  getAllClass() {
    axios.get('/base/member/class/getAll').then(
      (res: any) => {
        this.allClasses = res.data.data
      }
    )
  }

  getAllPlace() {
    axios.get(
      '/base/location/getAll'
    ).then(
      (res: any) => {
        this.placeList = res.data.data
    })
  }

  @Watch('productCode', { immediate: false, deep: true })
  onProductCodehanged(val: string, oldVal: string) {
    if (val.length >= 6) {
      this.submitProductCode()
    }
  }

  async submitProductCode() {
    await axios.post(
      '/product/findByCode', 
      { productCode: this.productCode })
    .then(
      (res: any) => {
        this.productDetail = res.data.data
        this.productDetail.qty = 1
        // this.productDetail.totalPrice = this.productDetail.retailPrice
      }
    )
  }

  newInvoiceItems: any = []

  submitPreSell() {
    console.log(this.productDetail.taxAmount, 'firest')
    const preSellThing = {
      productCode: this.productDetail.productCode,
      qty: this.productDetail.qty,
      retailPrice: Number(this.productDetail.retailPrice),
      discount: this.productDetail.discount,
      totalPrice: Number(this.productDetail.totalPriceAll),
      description: this.productDetail.description,
      productId: this.productDetail.id,
      discountType: this.productDetail.discountType,
      placeId: this.placeId,
      afterTax: Number(this.productDetail.totalPriceAll),
      taxAmount: Number(this.productDetail.taxAmount) * this.productDetail.qty,
      taxCode: this.productDetail.taxCode
    }

    const newInvoiceItem = {
      productId: this.productDetail.id,
      qty: this.productDetail.qty,
      discount: this.productDetail.discount,
      price: Number(this.productDetail.totalPriceAll),
      discountType: this.productDetail.discountType,
      taxRate: this.productDetail.taxRate,
      taxCode: this.productDetail.taxCode,
      taxAmount: Number(this.productDetail.taxAmount)
    }

    this.newInvoiceItems.push(newInvoiceItem )
    this.preSellList.push(preSellThing)
    this.productDetail = {}

    this.calTotalNumber()
  }

  changeSellProduct(row: any) {
    this.productDetail = row
  }

  selectMember(row: any) {
    this.selectedMember = row
    this.foundMemberList = []
  }

  findMember(){
      if (Number(this.memberNameFind)) {
        axios.post(
          '/base/member/find', 
          { search: this.memberNameFind })
        .then(
          (res: any) => {
            this.foundMemberList = res.data.data
          }
        )
      } else if (typeof this.memberNameFind === 'string') {
        axios.post(
          '/base/member/find', 
          { name: this.memberNameFind })
        .then(
          (res: any) => {
            this.foundMemberList = res.data.data
          }
        )
      }
     
  }

  calTotalNumber() {
    this.totalCalForm.totalCal = 0
    this.totalCalForm.taxTotal = 0
    
    this.preSellList.forEach(a => {
        
        this.totalCalForm.taxTotal += a.taxAmount
        this.totalCalForm.totalCal += a.totalPrice
        this.totalCalForm.totalPriceAll += a.afterTax
      }
    )
  }

  @Watch('totalCalForm.discountType', { immediate: true, deep: true })
  newTotal() {
    console.log(this.totalCalForm.totalPriceAll, 'test')
    if (this.totalCalForm.discountType === '%') {
      this.totalCalForm.totalPrice = ( this.totalCalForm.retailPrice * (1-(this.totalCalForm.discount/100)) ).toFixed(2)
      this.totalCalForm.totalPriceAll= ( this.totalCalForm.totalPriceAll* (1-(this.totalCalForm.discount/100)) ).toFixed(2)
    } else if (this.totalCalForm.discountType === '$') {
      this.totalCalForm.totalPrice = ( this.totalCalForm.retailPrice - this.totalCalForm.discount ).toFixed(2)
      this.totalCalForm.totalPriceAll = ( this.totalCalForm.totalPriceAll - this.totalCalForm.discount ).toFixed(2)
    } else {
      this.totalCalForm.totalPriceAll = this.totalCalForm.totalPriceAll
    }
   
  }

  refershNewTotal() {
    if (this.totalCalForm.discountType === '%') {
      this.totalCalForm.totalPrice = ( this.totalCalForm.retailPrice * (1-(this.totalCalForm.discount/100)) ).toFixed(2)
      this.totalCalForm.totalPriceAll= ( this.totalCalForm.totalPriceAll* (1-(this.totalCalForm.discount/100)) ).toFixed(2)
    } else if (this.totalCalForm.discountType === '$') {
      this.totalCalForm.totalPrice = ( this.totalCalForm.retailPrice - this.totalCalForm.discount ).toFixed(2)
      this.totalCalForm.totalPriceAll = ( this.totalCalForm.totalPriceAll - this.totalCalForm.discount ).toFixed(2)
    } else {
      this.totalCalForm.totalPriceAll = this.totalCalForm.totalPriceAll
    }
  }

  payPO() {
    if (this.totalCalForm.totalCal > 0 ) {
      this.dialogVisible = true
      this.payForm.totalPrice = this.totalCalForm.totalCal
      this.payForm.balance = this.totalCalForm.totalCal
    }
  }

  cancelPO() {
    this.preSellList = []
    this.selectedMember = {}
    this.totalCalForm = {}
  }

  paymentAction() {
    const payment = {
      method: this.payFormMethod.payMethod,
      amount: this.payFormMethod.amount
    }

    this.payForm.balance = this.payForm.balance - this.payFormMethod.amount

    if (this.payForm.balance < 0 ) {
      this.payForm.findRedemption = -0-this.payForm.balance
      this.payForm.balance = 0
    }

    this.payList.push(payment)
    this.payFormMethod = {}
  }

  

  submitMemberPayment() {
    const main = {
      totalAmount: this.payForm.totalPrice,
      memberId: this.selectedMember.id ,
      locationId: this.placeId,
      discount: this.totalCalForm.discount,
      discountType: this.totalCalForm.discountType,
      taxTotal: this.totalCalForm.taxTotal,
      newPaymentItems: this.payList,
      newInvoiceItems: this.newInvoiceItems
    }

    console.log(main)

    axios.post('/invoice/save', main).then(
       (res: any) => {
        this.$router.push(`/invoice/detail/${res.data.data.id}`)   
    })       
  }

  CancelMemberPayment() {
    this.dialogVisible = false
  }

  detailHandle(id: number) {
    this.$router.push({ path: `/invoice/detail/${id}` })
  }

  userPlaceId:number = 0

  getUserInfo() {
        axios.get('/sys/userInfo').then(res => {
          const placeId = res.data.data.placeId
          this.userPlaceId = placeId
          if (placeId > 0) {
            this.placeId = placeId
          }
           
        })
    }

}
</script>
<style scoped>

  .box-area {
    width: 100%;
    height: 105%;
    padding-bottom: 100px;
  }

    .field-area{
      padding-top: 20px;
      padding-left: 20px;
      padding-bottom: 20 px;
      padding-right: 20px;
      background-color: rgb(222, 222, 222);
    }

    .handle-box {
        padding-right: 50px;
    }
</style>

