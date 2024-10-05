<template>
  <div class="w-full bg-white p-1 shadow-lg rounded-lg">
    <div class="handle-box">
      <div class="handle-box p-1">
        <el-button icon="el-icon-back" circle @click="back"></el-button>
        <el-button icon="el-icon-circle-plus" circle v-if="readonlyMode === true" @click="startEdit()"></el-button>
      </div>
    </div>
    <el-form :model="editForm" :disabled="readonlyMode" class="grid lg:grid-cols-4 gap-3 px-6">
      <el-form-item class="lg:col-span-full">
          <el-upload
            class="upload-demo"
            :auto-upload="false"
            :file-list="fileList"
            :on-change="onChangeUpload"
            :on-remove="removeUploaded"
          >
          <el-button size="small" type="primary">Upload</el-button>
          <div slot="tip" class="el-upload__tip">Only upload JPG or PNG</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="Product Code"  prop="productCode" label-width="120px">
        <el-input v-model="editForm.productCode" autocomplete="off" readonly></el-input>
      </el-form-item>
      <el-form-item label="Product Name"  prop="productName" label-width="120px">
        <el-input v-model="editForm.productName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Item Code"  prop="itemCode" label-width="120px">
        <el-input v-model="editForm.itemCode" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Type" prop="type" label-width="120px">
        <el-select v-model="editForm.typeId" placeholder="Select" filterable class="w-full">
          <el-option
            v-for="typeItems in typeItem"
            :key="typeItems.id"
            :label="typeItems.typeName"
            :value="typeItems.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Brand Name"  prop="brandName" label-width="120px" class="lg:col-span-2">
        <el-input v-model="editForm.brandName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Unit"  prop="unit" label-width="120px">
        <el-input v-model="editForm.unit" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="Retail Price"  prop="retailPrice" label-width="120px">
        <el-input-number v-model="editForm.retailPrice" :step="0.01" :precision="2" class="w-full"></el-input-number>
      </el-form-item>
      <el-form-item label="Tax Information" prop="taxInformation" label-width="120px">
        <el-checkbox v-model="editForm.tax" />
      </el-form-item>
      <el-form-item label="Tax Type"  prop="invoiceNo" label-width="120px" v-if="editForm.tax">
        <el-select v-model="editForm.taxCode" placeholder="Select" filterable clearable class="w-full">
          <el-option
            class="text-black"
            v-for="items in taxesData"
            :key="items.taxCode"
            :label="items.label"
            :value="items.taxCode">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="Tax Amount"  prop="taxAmount" label-width="120px" v-if="editForm.tax">
        <el-input-number v-model="editForm.taxAmount" disabled class="w-full"></el-input-number>
      </el-form-item>
      <el-form-item label="After Tax"  prop="afterTax" label-width="120px" v-if="editForm.tax">
        <el-input-number v-model="editForm.afterTax" disabled class="w-full"></el-input-number>
      </el-form-item>
      
      <el-form-item label="Description"  prop="description" label-width="120px" class="lg:col-span-full">
        <el-input type="textarea" v-model="editForm.description"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer p-4">
      <el-button @click="resetForm('editForm')">Reset</el-button>
      <el-button :disabled="hideSaveBtn" type="primary" @click="submitForm('editForm')" v-if="!readonlyMode">{{ editForm.id? 'Update' : 'Create' }}</el-button>
    </div>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import VueBase64FileUpload from 'vue-base64-file-upload'
import type { UploadFile } from 'element-plus/es/components/upload/src/upload.type'
import moment from 'moment'
import { Component, Vue, Watch } from 'vue-property-decorator'
import QrcodeVue from 'qrcode.vue'
import { uploadImgToBase64 } from '@/utils/uploadImgToBase64'

@Component({
    components: {
        VueBase64FileUpload
    }
})
export default class ProductListDetail extends Vue {
  editForm: any = {}
  editFormRules: any = []
  readonlyMode: boolean = false
  hideSaveBtn: boolean = false
  

  fileList: any = []
  fileBase64Data: any = []
  taxesData: any = []
  taxDataSelected: any = {}

  typeItem: any = []

   created() {
    // this.getAlldept()
    this.getAllType()
    this.getAllTaxesData()
    /// this.getAllPlace()
    // this.getAllVendor()
    if (this.$route.params.id) {
      this.editForm.id = Number(this.$route.params.id)
      this.editHandle()      
    }
  }

  startEdit() {
    this.readonlyMode = false
  }

  getAllType() {
        axios.get(
            '/product/type/getAll'
        ).then(
            (res: any) => {
            this.typeItem = res.data.data
        })
  }
  onChangeUpload(file: UploadFile) {
        let testmsg = file.name.substring(file.name.lastIndexOf('.')+1)
        const isJpg = testmsg === 'jpg' || testmsg === 'png' || testmsg === 'JPG' || testmsg === 'PNG'
        const isLt2M = file.size / 1024 / 1024 < 3
        if (!isJpg) {
            this.fileList = this.fileList.filter(v => v.uid !== file.uid)
            this.$message.error('Only Upload jpg and png!')
        }
        if (!isLt2M) {
            this.fileList = this.fileList.filter(v => v.uid !== file.uid)
            this.$message.error('File size cannot over 3MB!')
        }
        if (isJpg && isLt2M){
            this.fileList.push(file)
        }
        this.imgToBase64()
  }

  imgToBase64() {
    this.fileList.map(async (file: any) => {
      const response: any = await uploadImgToBase64(file.raw)
      const dataBase64: string = response.data
      this.fileBase64Data.push({ fileName: file.name, dataBase64 })
      // const test = response as never
    })
  }

  removeUploaded() {
    this.fileList = []
  }

  editHandle(){
    axios.get(`/product/${this.$route.params.id}`).then(
      (res: any) => {
        this.readonlyMode = true
        this.editForm = res.data.data
      }
    )
  }

  resetForm(formName: string) {
    const refs: any = this.$refs[formName]
    refs.resetFields()
    this.editForm = {}
    if (this.$route.params.id) {
      this.editHandle()
    }
  }

  submitForm(formName: string) {
    //    const refs: any = this.$refs[formName]
     //   refs.validate((valid: any) => {
       //     if (valid) {
                console.log(this.fileBase64Data[0])
                axios.post('/product/' + (this.editForm.id ? 'update' : 'create'), this.editForm)
                    .then((res: any) => {
                        if (this.fileBase64Data[0]) {
                            const productCode = this.editForm.id ? this.editForm.productCode : res.data.data.productCode
                            axios.post('/product/findByCode', { productCode }).then(
                                ((res: any) => {
                                    const  productId = res.data.data.id
                                    this.fileBase64Data.forEach( (dataFile: any) => {
                                        console.log(dataFile)
                                        const { fileName, dataBase64 } = dataFile
                                        axios.post(
                                            '/product/file/upload',
                                            { productId, fileName, base64: dataBase64 }
                                        ).then(
                                            res=> {
                                                this.$notify({
                                                    title: '',
                                                    showClose: true,
                                                    message: 'Success to save',
                                                    type: 'success',
                                                })

                                                this.fileList = []
                                                this.fileBase64Data = []
                                                this.back()
                                            })
                                        })
                                    })
                                )
                            } else {
                                this.$notify({
                                    title: '',
                                    showClose: true,
                                    message: 'Success to save',
                                    type: 'success',
                                })
                                this.back()
                            }
                })
     //       } else {
                return false;
     //  //     }
       // })
  }

  back() {
    this.$router.push({ path: '/product/product' })
  }

  getAllTaxesData() {
    axios.get(
            '/system/country/tax/getAll'
        ).then(
            (res: any) => {

            let results = res.data.data
            const updates = results.map( x=> {
              return {
                ...x,
                taxRate: Number(x.taxRate),
                label: `${x.nationName} - ${x.countryName} ${x.taxCode} (${Number(x.taxRate) * 100}%)`
              }
            } )
            this.taxesData = updates
        })
  }

  @Watch('editForm.taxCode')
  onTaxCodeChanged1(val: string, oldVal: string) {
    const data = this.taxesData .find(item => item.taxCode === val)
    
    this.editForm.taxRate = data.taxRate
    this.editForm.taxAmount = (this.editForm.retailPrice * data.taxRate).toFixed(2)
    this.editForm.afterTax = (this.editForm.retailPrice + ( this.editForm.retailPrice * data.taxRate)).toFixed(2)
  }

  @Watch('editForm.retailPrice')
  onRetailPriceChanged1(val: number, oldVal: number) {
    

    this.editForm.taxAmount = (val * this.editForm.taxRate).toFixed(2)
    this.editForm.afterTax = (val + ( val * this.editForm.taxRate)).toFixed(2)
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
