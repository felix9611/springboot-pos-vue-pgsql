<template>
  <div class="container">

    <div class="handle-box">
            <el-form :inline="true">
                <el-form-item>
                    <el-input
                      v-model="searchForm.number"
                      placeholder="Invoice Code"
                      clearable
                    >
                    </el-input>
                </el-form-item>

                <el-form-item>
                  <el-date-picker
                    v-model="searchForm.createdAtStart"
                    type="datetime"
                    placeholder="Select Create From">
                  </el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-date-picker
                    v-model="searchForm.createdAtEnd"
                    type="datetime"
                    placeholder="Select Create To">
                  </el-date-picker>
                </el-form-item>

                <!--<el-form-item>
                    <el-button @click="clickUploadDialog">Upload Excel</el-button>
                </el-form-item>-->

                <el-form-item>
                    <el-button @click="listAll()">Find</el-button>
                </el-form-item>
            </el-form>
    </div>


    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange">
        <el-table-column
          sortable
          prop="number"
          label="Invoice Code">
        </el-table-column>
        <el-table-column 
          label="Location/Shop Code" 
          prop="placeCode">
        </el-table-column>
        <el-table-column 
          label="Location/Shop Name" 
          prop="placeName">
        </el-table-column>
        <el-table-column 
          label="Member Name" 
          prop="memberName">
        </el-table-column>
        <el-table-column 
          label="Member Phone" 
          prop="memberPhone">
        </el-table-column>
         <el-table-column 
          label="Member Class" 
          prop="mcName">
        </el-table-column>
        <el-table-column 
          label="Total Amount" 
          prop="totalAmount">
        </el-table-column>
        <el-table-column 
          label="Created At" 
          prop="createdAt">
        </el-table-column>
        <el-table-column 
          label="Void At" 
          prop="voidAt">
        </el-table-column>
        <el-table-column
          label="Void"
          prop="voidNum">
          <template slot-scope="scope">
            <el-tag size="small" v-if="scope.row.voidNum === 0" type="success">Active</el-tag>
            <el-tag size="small" v-if="scope.row.voidNum === 1" type="danger">Void</el-tag>
            <el-tag size="small" v-if="scope.row.voidNum === 2" type="warning">Have Refund</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          width="180px"
          label="Action">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="detailHandle(scope.row.id)">Detail</el-button>
              <el-button
              size="mini"
              type="danger"
              @click="voidInv(scope.row.id)">Delete</el-button>
          </template>
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
export default class InoviceList extends Vue {
  tableData: any = []
  searchForm: any = {
    limit: 10,
    page: 1
  }
  total: number = 0
  size: number|undefined
  current: number = 1
  placeId: number = 0
  handleSelectionChange: boolean = false

  

  created() {
    this.getUserInfo()
    this.listAll()
    
  }

  getUserInfo() {
    axios.get('/sys/userInfo').then(res => {
      const placeId = res.data.data.placeId
      this.searchForm = {
        ...this.searchForm,
        locationId: placeId,
      }
    })
    
  }

  listAll() {
    console.log(this.searchForm)
    axios.post('/invoice/list', this.searchForm).then(
      (res: any) => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total

        this.tableData.forEach(
          (res: any) => {
            const newCreated =  moment(new Date(res['createdAt'])).format('DD-MM-YYYY HH:MM')
            const newVoidAt =  res['voidAt'] ? moment(new Date(res['voidAt'])).format('DD-MM-YYYY HH:MM') : null
            res['createdAt'] = newCreated
            res['voidAt'] = newVoidAt
            return res
          }
        )
      }
    )
  }

  voidInv(id: number) {
    axios.delete(`/invoice/void/${id}`).then(
      (rs: any) =>
        {
          this.listAll()
        }
    )
  }

  handleSizeChange(val: any) {
    this.searchForm.limit = val
    this.listAll()
  }

  handleCurrentChange(val: any) {
    this.searchForm.page = val
    this.listAll()
  }

  detailHandle(id: number) {
    this.$router.push({ path: `/invoice/detail/${id}` })
  }

  
}
</script>