<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true">
        <el-form-item>
          <el-button @click="classAllList">Find</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="dialogVisible">Create</el-button>
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
        label="Name"
        width="130">
      </el-table-column>
      <el-table-column
        sortable
        prop="remark"
        label="Remark">
      </el-table-column>
      <el-table-column
        sortable
        prop="createdAt"
        label="Created At">
      </el-table-column>
      <el-table-column
        sortable
        prop="updatedAt"
        label="Updated At">
      </el-table-column>
      <el-table-column
        prop="icon"
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

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      layout="total, sizes, prev, pager, next, jumper"
      :page-sizes="[10, 20, 50, 100]"
      :current-page="current"
      :page-size="size"
      :total="total">
    </el-pagination>


    <el-dialog
      title="Form"
      :visible.sync="formDialog"
      width="700px"
      :before-close="handleClose">
      <el-form :model="editForm" ref="editForm">
        <el-form-item label="Name"  prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Remark"  prop="remark" label-width="150px">
          <el-input type="textarea" v-model="editForm.remark"></el-input>
         </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm('editForm')">{{ editForm.id? 'Update' : 'Create' }}</el-button>
        <el-button @click="resetForm('editForm')">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import { Component, Vue } from 'vue-property-decorator'
import moment from 'moment'

@Component
export default class MemberClass extends Vue {
  editForm: any = {}

  searchForm: any = {
    page: 1,
    limit: 10
  }

  tableData: any = []
  size: number|undefined
  current: number = 1
  total: number = 0

  formDialog: boolean = false
  delBtlStatu: boolean = true


  handleSizeChange(val: number) {
    this.searchForm.limit = val
    this.classAllList()
  }

  handleCurrentChange(val: number) {
    this.searchForm.page = val
    this.classAllList()
  }

  created() {
    this.classAllList()
  }

  handleSelectionChange(val: any) {
    this.delBtlStatu = val.length == 0
  }

  resetForm(formName: string) {
    const ref: any = this.$refs[formName]
    ref.resetFields()
    this.editForm = {}
  }

  submitForm(formName: string) {
    const validData: any = this.$refs[formName]
    validData.validate((valid: any) => {
            if (valid) {
                console.log(this.editForm)
                axios.post('/base/member/class/' + (this.editForm.id ? 'update' : 'create'), this.editForm)
                    .then((res: any) => {
                        this.classAllList()
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
                return false;
            }
      }
    )
  }


  classAllList() {
    axios.post(
      '/base/member/class/listAll',
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

  dialogVisible() {
    this.formDialog = true
  }

  handleClose() {
    this.formDialog = false
  }

  editHandle(id: number) {
    axios.get('/base/member/class/' + id).then(res => {
      this.editForm = res.data.data
      this.formDialog = true
    })
  }

  delItem(id: number) {
        axios.delete(`/base/member/class/remove/${id}`)
        .then((res: any) => {
            this.classAllList()
            this.$notify({
                title: '',
                showClose: true,
                message: 'Action is successful ',
                type: 'success'
            })
        })
  }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }

</style>
