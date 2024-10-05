<template>
  <div class="container">
    <!--<div class="handle-box">
      <el-form :inline="true">
        <el-form-item label="Place" prop="place" label-width="130px">
            <el-select v-model="searchForm.locationId" placeholder="Select" filterable>
              <el-option
                v-for="placeItems in placeList"
                :key="placeItems.id"
                :label="placeItems.placeName"
                :value="placeItems.id">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="productList()">Find</el-button>
        </el-form-item>
      </el-form>
    </div> -->
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange">
      <el-table-column
          sortable
          prop="productCode"
          label="Product Code">
        </el-table-column>
        <el-table-column
          prop="productName"
          label="Product Name">
        </el-table-column>
        <el-table-column
          prop="placeName"
          label="Place Name">
        </el-table-column>
        <el-table-column
          prop="qty"
          label="Qty">
        </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50, 100]"
      :current-page="searchForm.page"
      :page-size="searchForm.limit"
      :total="total">
    </el-pagination>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import moment from 'moment'
import { Component, Vue, Watch } from 'vue-property-decorator'

@Component
export default class ProductLocation extends Vue {
  tableData: any = []
  searchForm: any = {
    limit: 10,
    page: 1
  }
  total: number = 0
  size: number|undefined
  current: number = 1
  placeList: any = []

  created() {
    this.productList()
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

  productList() {
    axios.post(
      '/product/location/list',
      this.searchForm
    ).then(
            (res: any) => {
                this.tableData = res.data.data.records
                this.size = res.data.data.size
                this.current = res.data.data.current
                this.total = res.data.data.total

        this.tableData.forEach((re: any) => {
        const newCreated =  re.created ? moment(new Date(re.created)).format('DD-MM-YYYY HH:MM') : null
        const newUpdated =  re.updated ? moment(new Date(re.updated)).format('DD-MM-YYYY HH:MM') : null

        re['created'] = newCreated
        re['updated'] = newUpdated
        return re    
      })
    })
  }

  handleSizeChange(val: any) {
        this.searchForm.limit = val
        this.productList()
  }

  handleCurrentChange(val: any) {
    this.searchForm.page = val
    this.productList()
  }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>
