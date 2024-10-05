<template>
  <div class="container">
    <div class="handle-box">
      <el-button icon="el-icon-back" circle @click="back"></el-button>
        <el-form :model="itemTakeForm" ref="itemTakeForm">
          <el-form-item label="Asset Code"  prop="assetCode" label-width="100px">
            <el-input v-model="itemTakeForm.productCode" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="Place" prop="locationId" label-width="100px">
                    <el-select v-model="itemTakeForm.locationId" placeholder="Select" filterable>
                        <el-option
                        v-for="item in placeItem"
                        :key="item.id"
                        :label="item.placeName"
                        :value="item.id">
                        </el-option>
                    </el-select>
          </el-form-item>
          <el-form-item label="Qty"  prop="qty" label-width="100px">
            <el-input v-model="itemTakeForm.qty" autocomplete="off"></el-input>
          </el-form-item>
          <div>
            <el-button @click="resetForm()">Reset</el-button>
            <el-button type="primary" @click="submitForm()">Submit</el-button>
          </div>
      </el-form>
      <br>
      <br>
      <el-table
        ref="multipleTable"
        :data="stockTakeItemList"
        tooltip-effect="dark"
        style="width: 100%">
         <el-table-column
            prop="productCode"
            label="Product Code">
          </el-table-column>
          <el-table-column
            prop="productName"
            label="Product Place">
          </el-table-column>
           <el-table-column
            prop="plQty"
            label="Placed Qty">
          </el-table-column>
          <el-table-column
            prop="qty"
            label="Qty">
          </el-table-column>
          <el-table-column
            prop="timeAt"
            label="Time At">
          </el-table-column>
          <el-table-column
            prop="statu"
            label="Status">
            <template slot-scope="scope">
              <el-tag size="small" v-if="scope.row.plQty < scope.row.qty" type="danger">Stock Take Qty over than placed qty</el-tag>
              <el-tag size="small" v-else type="success">Correct</el-tag>
            </template>

            </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import moment from 'moment'
import { Component, Vue, Watch } from 'vue-property-decorator'

@Component
export default class StocktakeDtail extends Vue {
  placeItem: any = []
  itemTakeForm: any = {}
  statusItemNew: any = []
  stockTakeItemList: any = []
  itemFindForm: any = {
    limit: 5,
    page: 1,
    formId: this.$route.params.id
  }
  itemTotal: number = 0
  itemSize: number|undefined
  itemCurrent: number 
  get formId() {
    return this.$route.params.id
  }
  genralForm: any = {}

  created() {
    this.getAllPlace()
    this.getAllValueCode()
    this.listItem()
    this.itemTakeForm.formId = this.formId
  }

  resetForm() {
    this.itemTakeForm = {}
    this.itemTakeForm.formId = this.formId
  }

  getForm() {
    axios.get(`/stockTake/${this.formId}`).then((res: any)=>{ 
      this.genralForm = res.data.data
    })
  }

  getAllPlace() {
    axios.get(
      '/base/location/getAll'
    ).then(
      (res: any) => {
        this.placeItem = res.data.data
      }
    )
  }

  getAllValueCode() {
    axios.post(
      '/base/code_type/getAllValue',
      { type: 'StockTake' }
    ).then(
      (res: any) => {
        this.statusItemNew = res.data.data
    })
  }

  @Watch('itemTakeForm.productCode', { immediate: true, deep: true  })
  productDetal() {
    axios.post(
      '/product/findByCode', 
      { productCode: this.itemTakeForm.productCode })
    .then(
      (res: any) => {
        this.itemTakeForm.productName = res.data.data.productName
        this.itemTakeForm.productId = res.data.data.id
      }
    )
  }

  listItem() {
    axios.post(
      '/stockTake/detail/list', 
      this.itemFindForm
    ).then(
      (res: any) => {
        this.stockTakeItemList = res.data.data.records
        this.itemSize = res.data.data.size
        this.itemCurrent = res.data.data.current
        this.itemTotal = res.data.data.total

        this.stockTakeItemList.forEach((re: any) => {
          const newCheckTime =  re.timeAt ? moment(new Date(re.timeAt)).format('DD-MM-YYYY HH:MM') : null

          re['timeAt'] = newCheckTime
          return re
        })
      }
    )
  }

  submitForm() {
    axios.post(
      '/stockTake/item',
      this.itemTakeForm
      ).then(
        (res: any) => {
          this.$notify({
            title: '',
            showClose: true,
            message: 'Success to save',
            type: 'success',
          })
          this.listItem()
        }
      )
  }

  back() {
    this.$router.push({ path: '/stock/stocktake' })
  }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>
