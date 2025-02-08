<template>
  <div class="container">
    <div class="handle-box">
            <el-form :inline="true">
                <el-form-item>
                    <el-input
                      v-model="searchForm.productCode"
                      placeholder="Product Code"
                      clearable
                    >
                    </el-input>
                </el-form-item>

                <el-form-item prop="type">
                  <el-select v-model="searchForm.typeId" placeholder="Select Type" filterable class="w-full">
                    <el-option
                      v-for="typeItems in typeItem"
                      :key="typeItems.id"
                      :label="typeItems.typeName"
                      :value="typeItems.id">
                    </el-option>
                  </el-select>
                </el-form-item>

                <!--<el-form-item>
                    <el-button @click="clickUploadDialog">Upload Excel</el-button>
                </el-form-item>-->

                <el-form-item>
                    <el-button @click="productList()">Find</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="goToCreated()">Create</el-button>
                </el-form-item>

                <el-form-item>
                  <el-button @click="downloadTemplateExcel()">Download Template Excel</el-button>
                </el-form-item>

                <el-form-item>
                  <el-button @click="clickUploadDialog()">Upload Excel</el-button>
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
          prop="typeCode"
          label="Type Code">
        </el-table-column>
        <el-table-column
          sortable
          prop="typeName"
          label="Type Name">
        </el-table-column>
        <el-table-column
          sortable
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
    <el-table-column
      prop="icon"
      label="Action"
       width="420px">

      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="getQRCodeTag(scope.row)">View QR Code</el-button>
        <el-divider direction="vertical"></el-divider>
        <el-button
          size="mini"
          @click="viewsPhoto(scope.row.id)">View Photos</el-button>
        <el-divider direction="vertical"></el-divider>
        <el-button
          size="mini"
          @click="editHandle(scope.row.id)">Edit</el-button>
        <el-divider direction="vertical"></el-divider>
        <el-button
            size="mini"
            type="danger"
            @click="delItem(scope.row.id)">Delete</el-button>
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

    <el-dialog
      title="View Photos"
      :visible.sync="photoViewerDialog"
      width="600px"
      :before-close="closePhotoViewerDialog">
      <div v-for="item in fileBase64Data" :key="item.fileName">
                    <el-row  :gutter="20">
                        <el-col :span="15">
                            <p>Uploaded File Name: {{ item.fileName }}</p>
                        </el-col>
                        <el-col :span="5">
                            <el-button
                            size="mini"
                            type="danger"
                            @click="delItemFile(item.id, item.productId)">Delete</el-button>
                        </el-col>
                    </el-row>
                    <br>
                    <br>
                    <el-row>
                        <el-col>
                            <img :src="item.base64" style="width: 70%">
                        </el-col>
                    </el-row>
                    <el-divider ></el-divider>
      </div>
    </el-dialog>

    <el-dialog
      title="QR Code Tag"
      :visible.sync="qrCodeTagDialog"
      width="300px"
      :before-close="closeQRCodeDialog">
      <div>
        <p>
          Product Code: {{ productDetail.productCode }}
        </p>
        <p>
          Product Name: {{ productDetail.productName }}
        </p>
        <br>
        <br>
        <div>
        </div>
       <qrcode-vue :value="qrTagContent" :size="250" level="M" />
     </div>
    </el-dialog>

    <el-dialog
      title="Upload Excel"
      :visible.sync="uploaderDialog"
      width="700px"
      :before-close="closerUploadDialog">
        <el-upload
          class="upload-demo"
          :auto-upload="false"
          :file-list="fileList"
          :on-change="uploadFile"
          :on-remove="clearFile"
          >
            <el-button size="small" type="primary">Upload</el-button>
            <!--<div slot="tip" class="el-upload__tip">Only Excel</div>-->
            </el-upload>
     </el-dialog>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import VueBase64FileUpload from 'vue-base64-file-upload'
import type { UploadFile } from 'element-plus/es/components/upload/src/upload.type'
import { uploadImgToBase64 } from '@/utils/uploadImgToBase64'
import moment from 'moment'
import { Component, Vue } from 'vue-property-decorator'
import { downloadTempExcelFile, formatJson, readExcel } from '@/utils/importExcel'
import QrcodeVue from 'qrcode.vue'
import { excelHeader, formatData } from './excelHeaders'

@Component({
    components: {
        VueBase64FileUpload,
        QrcodeVue
    }
})
export default class ProductList extends Vue {
  uploaderDialog: boolean = false
  fileList: any = []
  dialogVisible: boolean = false
  searchForm: any = {
    limit: 10,
    page: 1
  }
  photoViewerDialog: boolean = false

  tableData: any = []
  total: number = 0
  size: number|undefined
  current: number = 1
  editForm: any = {}
  editFormRules: any = []
  productDetail: any = {}
  qrCodeTagDialog: boolean = false
  qrTagContent: string = ''
  fileBase64Data: any = []
  typeItem: any = []

  created() {
    this.productList()
    this.getAllType()
  }

  getAllType() {
        axios.get(
            '/product/type/getAll'
        ).then(
            (res: any) => {
            this.typeItem = res.data.data
        })
  }

  clickUploadDialog() {
    this.uploaderDialog = true
  }

  closerUploadDialog() {
    this.uploaderDialog = false
  }

  closeQRCodeDialog() {
    this.qrCodeTagDialog = false
  }
  
  clearFile() {
    this.fileList = []
  }

  goToCreated() {
    this.$router.push('/product/create')
  }

  productList() {
        axios.post(
            '/product/list',
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

  editHandle(id: number) {
     this.$router.push({ path: `/product/detail/${id}` })
  }

  closePhotoViewerDialog() {
    this.photoViewerDialog = false
  }

  delItem(id: number) {
        axios.delete(`/product/void/${id}`).then((res: any) => {
            this.productList()
            this.$notify({
                title: '',
                showClose: true,
                message: 'Action is successful ',
                type: 'success'
            })
        })
  }

  getQRCodeTag(product: any) {
    this.qrCodeTagDialog = true
    const { productCode, productName, typeName, updated } = product
    this.productDetail = product
    const download = moment().format('DD-MM-YYYY HH:MM')
    this.qrTagContent = `${productCode}|${productName}|${typeName}|Updated At:${updated}|Download Tag:${download}`
  }

  viewsPhoto(productId: number) {
    axios.post(
      '/product/loadFile', 
      { productId })
    .then((res: any)=>{
      this.fileBase64Data = res.data.data
      this.photoViewerDialog = true
      console.log(this.fileBase64Data)
    })
  }

 delItemFile(id: number, productId: number) {
        axios.delete(`/product/file/void/${id}`).then(res => {
            this.viewsPhoto(productId)
            this.$notify({
                title: '',
                showClose: true,
                message: 'Delete file success',
                type: 'success'
        })
      })
  } 
  downloadTemplateExcel() {
    downloadTempExcelFile(excelHeader, 'product_template.xlsx')
  }


  async uploadFile(file: any) {
    const data: any = await readExcel(file)
 
    const finalData = formatData(data)

    console.log(finalData)

    axios.post('/product/batch-create', finalData).then((res: any) => {
            if (res) {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Upload success',
                    type: 'success',
                })
                this.uploaderDialog = false
                this.productList()
                this.fileList = []
                file = []
    }
    })
  }

}
</script>