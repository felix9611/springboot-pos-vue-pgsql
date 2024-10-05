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
      tooltip-effect="dark">
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
          prop="typeCode"
          label="Type Code">
        </el-table-column>
        <el-table-column
          prop="typeName"
          label="Type Name">
        </el-table-column>
        <el-table-column
          prop="unit"
          label="Unit">
        </el-table-column>
        <el-table-column
          prop="retailPrice"
          label="Retail Price"
        >
        </el-table-column>
        <el-table-column
          prop="itemCode"
          label="Item Code"
        >
        </el-table-column>
        <el-table-column
          prop="created"
          label="Created At"
        >
        </el-table-column>
        <el-table-column
          prop="updated"
          label="Update At"
        >
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
export default class ProductListReport extends Vue {
  searchForm: any = {}
  tableData: any = []

  created() {
    this.productList()
  }

  productList() {
    axios.post(
      '/product/list/all',
      this.searchForm
    ).then(
      (res: any) => {
        this.tableData = res.data.data

        this.tableData.forEach((re: any) => {
          const newCreated =  re.created ? moment(new Date(re.created)).format('DD-MM-YYYY HH:MM') : null
          const newUpdated =  re.updated ? moment(new Date(re.updated)).format('DD-MM-YYYY HH:MM') : null

          re['created'] = newCreated
          re['updated'] = newUpdated
          return re     
      })
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
    doc.save(`${time}_product_list.pdf`)
  }
  
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>
