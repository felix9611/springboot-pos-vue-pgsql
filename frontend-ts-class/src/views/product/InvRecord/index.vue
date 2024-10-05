<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true">
        <el-form-item>
          <el-date-picker
            v-model="searchForm.timeAtFrom"
            type="datetime"
            placeholder="Select From">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="searchForm.timeAtTo"
            type="datetime"
            placeholder="Select To">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button @click="invRecord()">Find</el-button>
        </el-form-item>

        <!--<el-form-item>
          <el-button
            size="mini"
            type="danger"
            @click="generatePDF()">Download PDF
          </el-button>
        </el-form-item> -->
      </el-form>
    </div>

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
          sortable
          prop="productName"
          label="Product Name">
        </el-table-column>
        <el-table-column
          sortable
          prop="placeFromCode"
          label="Place From Code">
        </el-table-column>
        <el-table-column
          sortable
          prop="placeFromName"
          label="Place From Name">
        </el-table-column>
        <el-table-column
          sortable
          prop="placeToCode"
          label="Place To Code">
        </el-table-column>
        <el-table-column
          sortable
          prop="placeToName"
          label="Place To Name">
        </el-table-column>
        <el-table-column
          sortable
          prop="qty"
          label="Qty">
        </el-table-column>
        <el-table-column
          sortable
          prop="timeAt"
          label="Time At">
        </el-table-column>
        <el-table-column
          label="Status"
          prop="status">
          <template slot-scope="scope">
            <el-tag size="small" v-if="scope.row.qty > 0" type="success">Active</el-tag>
            <el-tag size="small" v-if="scope.row.qty < 0" type="success">Stock Move</el-tag>
            <el-tag size="small" v-else-if="scope.row.locTo < 0" type="danger">Write Off</el-tag>
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
import { Component, Vue } from 'vue-property-decorator'
import QrcodeVue from 'qrcode.vue'
import { uploadImgToBase64 } from '@/utils/uploadImgToBase64'

@Component
export default class InvRecord extends Vue {
  tableData: any = []
  searchForm: any = {
    page: 1,
    limit: 10
  }
  total: number = 0
  size: number|undefined
  current: number = 1

  created() {
    this.invRecord()
  }

  invRecord() {
    axios.post('/inv/record/list', this.searchForm)
    .then((res: any)=>{
      this.tableData = res.data.data.records
      this.size = res.data.data.size
      this.current = res.data.data.current
      this.total = res.data.data.total

      this.tableData.forEach((re: any) => {
        console.log(re.timeAt)
        const newTimeAt =  moment(new Date(re.timeAt)).format('DD-MM-YYYY HH:MM')

        re['timeAt'] = newTimeAt
        return re    
      })
    })
  }

  handleSizeChange(val: any) {
    this.searchForm.limit = val
    this.invRecord()
  }

  handleCurrentChange(val: any) {
    this.searchForm.page = val
    this.invRecord()
  }
}
</script>