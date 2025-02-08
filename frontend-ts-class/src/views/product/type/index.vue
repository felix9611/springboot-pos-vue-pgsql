<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true">
        <el-form-item prop="typeName" label-width="100px">
          <el-input
            v-model="searchForm.name"
            placeholder="Type Name/Code"
            clearable
          />
        </el-form-item>

        <el-form-item>
          <el-button @click="productTypeList">Find</el-button>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="dialogVisible = true">Create</el-button>
        </el-form-item>

        <el-form-item>
          <el-button @click="downloadTemplateExcel()">Download Template Excel</el-button>
        </el-form-item>

        <el-form-item>
          <el-button @click="clickUploadDialog">Upload Excel</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-dialog
      title="Form Box"
      :visible.sync="dialogVisible"
      width="700px"
      :before-close="handleClose">
        <el-form :model="editForm" :rules="editFormRules" ref="editForm">
          <el-form-item label="Type Code" prop="typeCode" label-width="100px">
            <el-input
              v-model="editForm.typeCode"
              placeholder="Type Code"
              clearable
            />
          </el-form-item>
          <el-form-item label="Type Name" prop="typeName" label-width="100px">
            <el-input
              v-model="editForm.typeName"
              placeholder="Type Name"
              clearable
            />
          </el-form-item>
          <el-form-item label="Other Name"  prop="deptOtherName" label-width="100px">
            <el-input type="textarea" v-model="editForm.typeOtherName"></el-input>
          </el-form-item>
          <el-form-item label="Other Name"  prop="deptOtherName" label-width="100px">
            <el-input type="textarea" v-model="editForm.remark"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm('editForm')">{{ editForm.id? 'Update' : 'Create' }}</el-button>
          <el-button @click="resetForm('editForm')">Cancel</el-button>
        </div>
    </el-dialog>

     <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%">
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
          prop="created"
          label="Created At">
        </el-table-column>
        <el-table-column
          sortable
          prop="created"
          label="Updated At">
        </el-table-column>
        <el-table-column
          prop="icon"
          width="260px"
          label="Action">
            <template slot-scope="scope">
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
                        <!--<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
                    </el-upload>
        </el-dialog>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import { downloadTempExcelFile, formatJson, readExcel } from '@/utils/importExcel'
import moment from 'moment'
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class ProductType extends Vue {
  dialogVisible: boolean = false
  searchForm: any = {
    limit: 10,
    page: 1
  }
  tableData: any = []
  total: number = 0
  size: number|undefined
  current: number = 1

  editForm: any = {}
  editFormRules: any = []
  uploaderDialog: boolean = false
  fileList: any = []

  testEcelHeader1 = [
        'Type Code',
        'Type Name',
        'Remark'
    ]

    testEcelHeader2 = [
        'typeCode',
        'typeName',
        'remark'
    ]

  created() {
    this.productTypeList()
  }

  downloadTemplateExcel() {
        downloadTempExcelFile(this.testEcelHeader1, 'asset_types_template.xlsx')
    }

  async uploadFile(file: any) {
        const data = await readExcel(file)
        const reData = formatJson(this.testEcelHeader1, this.testEcelHeader2, data)

        axios.post('/product/type/batch-create', reData).then((res: any) => {
            if (res) {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Upload success',
                    type: 'success',
                })
                this.uploaderDialog = false
                this.productTypeList()
                this.fileList = []
                file = undefined
            }
        })
    }

  
  clearFile() {
    this.fileList = []
  }

  productTypeList() {
    axios.post(
      '/product/type/list',
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
      }
    )
  }

  resetForm(formName: string) {
    const refs: any = this.$refs[formName]
    refs.resetFields()
    this.editForm = {}
  }

  handleClose() {
    this.resetForm('editForm')
  }

  submitForm(formName: string) {
        const validData: any = this.$refs[formName]
        validData.validate((valid: any) => {
            if (valid) {
                console.log(this.editForm)
                axios.post('/product/type/' + (this.editForm.id ? 'update' : 'create'), this.editForm)
                    .then((res: any) => {
                        this.productTypeList()
                        this.$notify({
                            title: 'Msg',
                            showClose: true,
                            message: 'Action is successful ',
                            type: 'success',
                        })
                    this.handleClose()
                    this.dialogVisible = false
                })
            } else {
                return false;
            }
    
    })
  }
  editHandle(id: number) {
        axios.get('/product/type/'+ id).then((res: any) => {
            this.editForm = res.data.data
            this.dialogVisible = true
        })
    }

    delItem(id: number) {
        axios.delete(`/product/type/void/${id}`).then((res: any) => {
            this.productTypeList()
            this.$notify({
                title: '',
                showClose: true,
                message: 'Action is successful ',
                type: 'success'
            })
        })
    }

    closerUploadDialog() {
        this.fileList = []
        this.uploaderDialog = false
    }

    clickUploadDialog() {
        this.fileList = []
        this.uploaderDialog = true
    }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>
