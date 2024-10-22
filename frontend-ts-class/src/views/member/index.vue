<template>
  <div class="container">
    <div>
      <el-form :inline="true">
        <el-form-item>
          <el-input
            v-model="searchForm.name"
            placeholder="Phone"
            clearable
          >
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="searchForm.phone"
            placeholder="Phone"
            clearable
          >
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="memberAllList">Find</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="dialogVisible">Create</el-button>
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
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        sortable
        prop="name"
        label="Person Name">
      </el-table-column>
      <el-table-column
        sortable
        prop="phone"
        label="Phone">
      </el-table-column>
      <el-table-column
        sortable
        prop="email"
        label="Email">
      </el-table-column>
      <el-table-column
        sortable
        prop="fax"
        label="Fax">
      </el-table-column>
      <el-table-column
        sortable
        prop="fax"
        label="Fax">
      </el-table-column>
      <el-table-column
        sortable
        prop="createdAt"
        label="Created At">
      </el-table-column>
      <el-table-column
        sortable
        prop="updatedAt"
        label="Created At">
      </el-table-column>
      <el-table-column
        prop="icon"
        label="Action"
        width="280">
        <template slot-scope="scope"> 
          <el-button
           size="mini"
            @click="specialDays(scope.row.id)">Special Days</el-button>
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

    <el-dialog
      title="Special Days"
      :visible.sync="specialDaysDialog"
      width="1000px"
      :before-close="handleSpecialDaysClose">
      <el-form :model="memberSpecialDaysForm" ref="memberSpecialDaysForm">
        <el-form-item label="Name"  prop="name" label-width="120px">
          <el-input v-model="memberSpecialDaysForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Remark"  prop="remark" label-width="120px">
          <el-input type="textarea" v-model="memberSpecialDaysForm.remark"></el-input>
        </el-form-item>
        <el-form-item label="Date" prop="date" label-width="120px">
          <el-date-picker
            v-model="memberSpecialDaysForm.date"
            type="datetime"
            placeholder="Select date and time">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div>
        <el-button  @click="resetSpecialDaysForm('memberSpecialDaysForm')">Reset</el-button>
        <el-button type="primary" @click="submitSpecialDaysForm('memberSpecialDaysForm')">{{ memberSpecialDaysForm.id? 'Update' : 'Create' }}</el-button>
      </div>
      <br>
      <br>
        <el-table
        ref="multipleTable"
        :data="specialDaysData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          sortable
          prop="name"
          label="Name">
        </el-table-column>
        <el-table-column
          sortable
          prop="remark"
          label="Remark">
        </el-table-column>
        <el-table-column
          sortable
          prop="date"
          label="Date">
        </el-table-column>
        <el-table-column
          sortable
          prop="createdAt"
          label="Created At">
        </el-table-column>
        <el-table-column
          sortable
          prop="updatedAt"
          label="Created At">
        </el-table-column>
        <el-table-column
          prop="icon"
          label="Action"
          width="200px">
          <template slot-scope="scope"> 
            <el-button
              size="mini"
              @click="editSpecialDaysHandle(scope.row.id)">Edit</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button
              size="mini"
              type="danger"
              @click="delSpecialDaysItem(scope.row.id)">Delete</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
      @size-change="handleSpceialDaySizeChange"
      @current-change="handleSpceialDayCurrentChange"
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50, 100]"
      :current-page="current"
      :page-size="size"
      :total="total">
    </el-pagination>
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
import { Component, Vue } from 'vue-property-decorator'
import moment from 'moment'
import { excelHeader, formatSpecialDate } from './excelHeader'
import { formatJson, readExcel, saveJsonToExcel, downloadTempExcelFile } from '@/utils/importExcel'
import { ElSwitch } from 'element-ui/types/switch'

@Component
export default class Member extends Vue {
  searchForm: any = {
    page: 1,
    limit: 10
  }
  memberForm: any = {}

  tableData: any = []
  specialDayData: any = []
  size: number|undefined
  current: number = 1
  total: number = 0
  formDialog: boolean = false
  uploaderDialog: boolean = false
  fileList: any = []

  allClasses: any = []

  specialDaysDialog: boolean = false
  specialDaysFindForm: any = {
    page: 1,
    limit: 10
  }
  specialDaysData: any = []
  memberSpecialDaysForm: any = {}
  memberId: number = 0

  downloadTemplateExcel() {
    downloadTempExcelFile(excelHeader, 'member_template.xlsx')
  }

  clickUploadDialog() {
    this.uploaderDialog = true
  }

  closerUploadDialog() {
    this.uploaderDialog = false
  }

  clearFile() {
    this.fileList = []
  }

  created() {
    this.memberAllList()
    this.getAllClass()
  }

  async uploadFile(file: any) {
    const data: any = await readExcel(file)
 
    const finalData = formatSpecialDate(data)

    console.log(finalData)

    axios.post('/base/member/batch-create', finalData).then((res: any) => {
            if (res) {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Upload success',
                    type: 'success',
                })
                this.uploaderDialog = false
                this.memberAllList()
                this.fileList = []
                file = undefined
            }
    })
   


  }

   handleSpceialDaySizeChange(val: number) {
    this.searchForm.limit = val
    this.specialDays(this.memberId)
  }
i
  handleSpceialDayCurrentChange(val: number) {
    this.searchForm.page = val
    this.specialDays(this.memberId)
  }

  getAllClass() {
    axios.get('/base/member/class/getAll').then(
      (res: any) => {
        this.allClasses = res.data.data
      }
    )
  }

  editHandle(id: number) {
    this.$router.push({ path: `/base/member/${id}` })
  }

  memberAllList() {
    axios.post(
      '/base/member/listAll',
      this.searchForm
    ).then(
      (res: any) => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total

        this.tableData.forEach((re: any) => {
          const newCreated =  re.createdAt ? moment(new Date(re['createdAt'])).format('DD-MM-YYYY HH:MM') : null
          const newUpdated =  re.updatedAt ? moment(new Date(re.updatedAt)).format('DD-MM-YYYY HH:MM') : null

          re['createdAt'] = newCreated
          re['updatedAt'] = newUpdated
          return re    
        })
      }
    )
  }

  resetForm(formName: string) {
    const ref: any = this.$refs[formName]
    ref.resetFields()
    this.memberForm = {}
  }

  resetSpecialDaysForm(formName: string) {
    const ref: any = this.$refs[formName]
    ref.resetFields()
    this.memberSpecialDaysForm = {}
  }

  submitBaseForm(formName: string) {
    const validData: any = this.$refs[formName]
    validData.validate((valid: any) => {
            if (valid) {
                console.log(this.memberForm)
                axios.post('/base/member/' + (this.memberForm.id ? 'update' : 'create'), this.memberForm)
                    .then((res: any) => {
                        this.memberAllList()
                        this.$notify({
                            title: 'Msg',
                            showClose: true,
                            message: 'Action is successful ',
                            type: 'success',
                        })
                    this.handleClose()
                    this.formDialog = false
                })
            } else {
                return false
            }
      }
    )
  }

  handleClose() {
    this.formDialog = false
  }

  handleSpecialDaysClose() {
    this.specialDaysDialog = false
  }

  dialogVisible() {
    this.$router.push({ path: '/base/member/create' })
    // this.formDialog = true
  }

  specialDays(memberId: number) {
    this.specialDaysDialog = true
    this.memberId = memberId
    this.memberSpecialDaysForm.memberId = memberId

    axios.post('/base/member/specialDay/list', { memberId, ...this.specialDaysFindForm })
    .then((res: any)=> {
      this.specialDaysData = res.data.data.records

      this.specialDaysData.forEach((re: any) => {
        const newDate =  re.date ? moment(new Date(re['date'])).format('DD-MM-YYYY') : null
        const newCreated =  re.createdAt ? moment(new Date(re['createdAt'])).format('DD-MM-YYYY HH:MM') : null
        const newUpdated =  re.updatedAt ? moment(new Date(re.updatedAt)).format('DD-MM-YYYY HH:MM') : null

        re['createdAt'] = newCreated
        re['updatedAt'] = newUpdated
        re['date'] = newDate
        return re    
      })
    })
  }

  delItem(id: number) {
        axios.delete(`base/member/remove/${id}`).then((res: any) => {
            this.memberAllList()
            this.$notify({
                title: '',
                showClose: true,
                message: 'Action is successful ',
                type: 'success'
            })
        })
    }

  submitSpecialDaysForm(formName: string) {
    const validData: any = this.$refs[formName]
    validData.validate((valid: any) => {
        if (valid) {
          axios.post('/base/member/specialDay/' + (this.memberSpecialDaysForm.id ? 'update' : 'save'), this.memberSpecialDaysForm)
          .then((res: any) => {
            this.specialDays(this.memberSpecialDaysForm.memberId)
            this.$notify({
              title: 'Msg',
              showClose: true,
              message: 'Action is successful ',
            type: 'success',
          })
        })
      } else {
        return false;
      }
    })
  }

  editSpecialDaysHandle(id: number) {
    axios.get(`/base/member/specialDay/${id}`)
    .then((res: any)=> {
      this.memberSpecialDaysForm = res.data.data
    })
  }

  delSpecialDaysItem(id: number) {}

}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }

</style>
