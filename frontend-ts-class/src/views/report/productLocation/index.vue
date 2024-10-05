<template>
  <div class="container">
    <div class="handle-box">
      <el-button
        size="mini"
        type="danger"
        @click="generatePDF">
          Download PDF
      </el-button>
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
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import { pdfColumns } from './pdfColumns'
import jsPDF from 'jspdf'
import autoTable from 'jspdf-autotable'
import moment from 'moment'
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class ProductLocationReport extends Vue {
  searchForm: any = {}
  tableData: any = []

  created() {
    this.productList()
  }

  productList() {
    axios.post(
      '/product/location/list/all',
      this.searchForm
    ).then(
      (res: any) => {
        this.tableData = res.data.data   
      })
  }

  generatePDF() {
    const doc = new jsPDF('p', 'pt', 'a4', true)

    const time = moment().format('DD-MM-YYYY HH:mm:ss')

    doc.text(`Download At: ${time}`, 40, 40)
    autoTable(doc, {
            startY: 50,
            columns: pdfColumns,
            body: this.tableData,
            styles: {
                font: 'NotoSansCJKtc'
            }
    })
    doc.save(`${time}_product_location.pdf`)
  }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>
