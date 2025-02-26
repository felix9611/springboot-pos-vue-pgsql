<template>
  <div class="container">
    <el-row class="col-pad" :span="24">
      <el-col :span="12">
        <el-form :model="editForm">
           <el-form-item label="Product Code"  prop="productCode" label-width="130px">
            <el-input v-model="stockInForm.productCode" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="Product Name"  prop="productName" label-width="130px">
            <el-input v-model="stockInForm.productName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="Qty"  prop="qty" label-width="130px">
            <el-input-number v-model="stockInForm.qty"></el-input-number>
          </el-form-item>
          <el-form-item label="Cost"  prop="cost" label-width="130px">
            <el-input-number v-model="stockInForm.cost"></el-input-number>
          </el-form-item>
          <el-form-item label="Place From" prop="placeFrom" label-width="130px">
            <el-select v-model="stockInForm.oldPlace" placeholder="Select" filterable>
              <el-option
                v-for="placeItems in placeList"
                :key="placeItems.id"
                :label="placeItems.placeName"
                :value="placeItems.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Place To" prop="placeTO" label-width="130px">
            <el-select v-model="stockInForm.newPlace" placeholder="Select" filterable>
              <el-option
                v-for="placeItems in placeList"
                :key="placeItems.id"
                :label="placeItems.placeName"
                :value="placeItems.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div>
          <el-button @click="resetForm()">Reset</el-button>
          <el-button type="primary" @click="submitForm()">Submit</el-button>
        </div>
      </el-col>
      <el-col class="col-pad" :span="12">
        <el-table
          ref="multipleTable"
          :data="stockInList"
          tooltip-effect="dark">
          <el-table-column
            sortable
            prop="productCode"
            label="Product Code">
          </el-table-column>
          <el-table-column
            sortable
            prop="productName"
            label="Product Name">
          </el-table-column>
          <el-table-column
            prop="placeNameFrom"
            label="From Place Name">
          </el-table-column>
           <el-table-column
            prop="placeNameTo"
            label="To Place Name">
          </el-table-column>
           <el-table-column
            sortable
            prop="qty"
            label="Qty">
          </el-table-column>
        </el-table>
        <br>
        <br>
        <div>
          <el-button @click="resetList()">Reset</el-button>
          <el-button type="primary" @click="submitList()">Submit</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import moment from 'moment'
import { Component, Vue, Watch } from 'vue-property-decorator'

@Component
export default class StockIn extends Vue {
  stockInForm: any = {}
  stockInList: any = []

  placeList: any = []

  created() {
    this.getAllPlace()
  }

  getAllPlace() {
    axios.get(
      '/base/location/getAll'
    ).then(
      (res: any) => {
        this.placeList = res.data.data
    })
  }

  @Watch('stockInForm.productCode', { immediate: true, deep: true })
  productDetal() {
    axios.post(
      '/product/findByCode', 
      { productCode: this.stockInForm.productCode })
    .then(
      (res: any) => {
        this.stockInForm.productName = res.data.data.productName
        this.stockInForm.productId = res.data.data.id
      }
    )
  }

  @Watch('stockInForm.newPlace', { immediate: true, deep: true })
  locaationDetal() {
    if (this.stockInForm.newPlace) {
      axios.get(`/base/location/${this.stockInForm.newPlace}`)
      .then(
        (res: any) => {
          this.stockInForm.placeNameTo = res.data.data.placeName
        }
      )
    }
  }

  @Watch('stockInForm.oldPlace', { immediate: true, deep: true })
  locaationFronDetal() {
    if (this.stockInForm.oldPlace) {
      axios.get(`/base/location/${this.stockInForm.oldPlace}`)
      .then(
        (res: any) => {
          this.stockInForm.placeNameFrom = res.data.data.placeName
        }
      )
    }
  }

  submitForm() {
    this.stockInList.push(this.stockInForm)
    if (this.stockInForm !== []) {
      this.stockInList.push.apply(this.stockInList, this.stockInForm)
    }
    this.stockInForm = {}
    console.log(this.stockInList)
  }

  resetList() {
    this.stockInList = []
  }

  async submitList() {
    await axios.post('/product/location/renew', this.stockInList).then((res: any) => {
      if (res.data.code === 200) {
        this.$notify({
          title: '',
          showClose: true,
          message: 'Success to save',
          type: 'success'
        })
        this.stockInList = []
      }
    })

    
    /* this.stockInList.forEach((rs: any, i: number) => {
      setTimeout(
        function(){
          axios.post('/product/location/find',
          {
            productId: rs.productId,
            locationId: rs.placeFrom,
          }).then( (rc: any)=> {
            const oldData = rc.data.data
            if (rs.qty > oldData.qty) {
              
            } else {
              const renewQty = oldData.qty - rs.qty
              axios.post('/product/location/renew', {
                productId: rs.productId,
                oldPlace: rs.placeFrom,
                qty: renewQty,
                newPlace: rs.placeTo,
                newPlaceQty: rs.qty,
                cost: rs.cost
              })

              axios.post('/product/location/save',{
                productId: rs.productId,
                locationId: rs.placeTo,
                qty: rs.qty,
                totalPrice: rs.cost
              })
          }
        })
      } ,2000 * i)
      this.stockInList = []
      this.$notify({
          title: '',
          showClose: true,
          message: 'Success to save',
          type: 'success'
      })
    })*/
  } 
}
</script>
<style>
  .col-pad{
    padding-left: 30px;
    padding-right: 30px;
  }
</style>