<template>
  <div class="container">
    <div class="handle-box">
      <el-form :inline="true">
        <el-form-item label="Type Name" prop="typeName" label-width="100px">
          <el-input
            v-model="searchForm.typeName"
            placeholder="Type Name"
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button @click="productTypeList">Find</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="dialogVisible = true">Create</el-button>
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
  </div>
</template>
<script lang="ts">
import axios from '@/axios'
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


  created() {
    this.productTypeList()
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
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>
