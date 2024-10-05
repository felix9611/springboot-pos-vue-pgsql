<template>
  <div class="container">
    <div class="handle-box">
      <div class="handle-box">
        <el-button icon="el-icon-back" circle @click="back()"></el-button>
        <el-button
          class="text-[1.1rem]"
          size="mini"
          type="danger"
          v-on:click.prevent="generatePDF()">
            Download Invoice
        </el-button>
      </div>
    </div>
    
    <el-form :model="detailForm" :disabled="true" class="grid lg:grid-cols-4 gap-3">
      <el-form-item label="Invoice Code"  label-width="120px">
        <el-input v-model="detailForm.number" autocomplete="off" readonly></el-input>
      </el-form-item>
      <el-form-item label="Member Name"  label-width="120px">
        <el-input v-model="detailForm.memberName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Member Phone"  label-width="120px">
        <el-input v-model="detailForm.memberPhone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Member Class"  label-width="120px">
        <el-input v-model="detailForm.mcName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Shop Name"  label-width="120px">
        <el-input v-model="detailForm.placeName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Zip Code"  label-width="120px">
        <el-input v-model="detailForm.zipCode" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Country"  label-width="120px">
        <el-input v-model="detailForm.country" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Address"  label-width="120px" class="lg:col-span-2">
        <el-input v-model="detailForm.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Total Amount"  label-width="120px">
        <el-input v-model="detailForm.totalAmount" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Tax"  label-width="120px">
        <el-input v-model="detailForm.taxTotal" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Created Date"  label-width="120px">
        <el-input v-model="detailForm.createdAt" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Void Date"  label-width="120px" v-if="detailForm.voidAt">
        <el-input v-model="detailForm.voidAt" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Tax / GST No."  label-width="120px">
        <el-input v-model="detailForm.taxRefNo" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <br>
      <h2 class="px-6">Payment</h2>
    <br>
    <el-table
      class="px-5"
      ref="multipleTable"
      :data="itemPay"
      tooltip-effect="dark">
      <el-table-column 
          label="Amount" 
          prop="amount">
        </el-table-column>
       <el-table-column 
          label="Method" 
          prop="method">
        </el-table-column>
    </el-table>
    <br>
      <h2 class="px-6">Items List</h2>
    <br>
    <el-table
      class="px-5"
      ref="multipleTable"
      :data="items"
      tooltip-effect="dark"
      id="tab1">
      <el-table-column 
          label="Product Code" 
          prop="productCode">
        </el-table-column>
        <el-table-column 
          label="Product Name" 
          prop="productName">
        </el-table-column>
       <el-table-column 
          label="Qty" 
          prop="qty">
        </el-table-column>
        <el-table-column 
          label="Price" 
          prop="price">
        </el-table-column>
        <el-table-column 
          label="Discount" 
          prop="discount">
        </el-table-column>
        <el-table-column 
          label="Discount Type" 
          prop="discountType">
        </el-table-column>
        <el-table-column 
          label="Discount Type" 
          prop="discountType">
        </el-table-column>
        <el-table-column 
          label="Tax Type" 
          prop="taxType">
        </el-table-column>
        <el-table-column 
          label="Tax" 
          prop="taxAmount">
        </el-table-column>
    </el-table>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import moment from 'moment'
import { Component, Vue, Watch } from 'vue-property-decorator'
import jsPDF from 'jspdf'
import autoTable from 'jspdf-autotable'

import { paymentColumns, itemsColumns } from './pdfColumns'

@Component
export default class InoviceDetail extends Vue {
  get invoiceId() {
    return this.$route.params.id
  }
  detailForm: any = {}
  itemPay: any = []
  items: any = []

  created() {
    this.getDetail()
    this.getPayment()
    this.getItem()
    // this.generatePDF()
  }

  getDetail() {
    axios.get(`/invoice/detail/${this.invoiceId}`).then(
      (res: any) => {
        this.detailForm = res.data.data
        this.detailForm.createdAt = moment(new Date(this.detailForm.createdAt)).format('DD-MM-YYYY HH:MM')
        this.detailForm.voidAt = this.detailForm.voidAt ? moment(new Date(this.detailForm.voidAt)).format('DD-MM-YYYY HH:MM') : null
      }
    )
  }

  getPayment() {
    axios.get(`/payment/list/${this.invoiceId}`).then(
      (res: any) => {
        this.itemPay = res.data.data
      }
    )
  }

  getItem() {
    axios.get(`/invoice/item/${this.invoiceId}`).then(
      (res: any) => {
        this.items = res.data.data
        console.log(res)
      }
    )
  }

  back() {
    this.$router.push({ path: '/invoice' })
  }

  generatePDF() {
    console.log('hey')
    const doc = new jsPDF('p', 'pt', 'a4', true)

    autoTable(doc, {
            startY: 240,
            columns: itemsColumns,
            body: this.items,
            styles: {
                font: 'NotoSansCJKtc'
            }
    })

    doc.setFontSize(12)
    doc.text('ABC Company', 40, 30)


    doc.text(`Invoice No. : ${this.detailForm.number}`, 40, 60)
    doc.text(`Member Name : ${this.detailForm.memberName}`, 280, 60)
    doc.text(`Phone : ${this.detailForm.memberPhone}`, 280, 80)
    doc.text(`Member Class : ${this.detailForm.mcName}`, 280, 100)

    doc.text(`Shop Name : ${this.detailForm.placeName}`, 40, 120)
    doc.text(`Zip Code : ${this.detailForm.zipCode}`, 250, 120)
    
    doc.text(`Address : ${this.detailForm.address}`, 40, 140)
    doc.text(`Date.: ${this.detailForm.createdAt}`, 40, 160)
    doc.text(`Country : ${this.detailForm.country}`, 250, 160)

    doc.text(`Total : $ ${this.detailForm.totalAmount}`, 40, 200)
    doc.text(`Tax. : $ ${this.detailForm.taxTotal}`, 40, 215)
    doc.text(`Tax/GST no. : ${this.detailForm.taxRefNo}`, 180, 215)

    doc.output("dataurlnewwindow")
    //doc.save(`${this.detailForm.number}.pdf`)

    
   // const refs: any = this.$refs
   // refs.myiframe.contentWindow.document = doc.output('datauri')

   //  document.getElementById('main-iframe').setAttribute('src', doc.output('bloburl'));
  }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }

    .el-container {
      width: 100%;
      height: 100%;
      background-color: white;
    }
</style>
