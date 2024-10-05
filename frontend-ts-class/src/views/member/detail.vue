<template>
  <div class="container">
     <div class="handle-box">
        <el-button icon="el-icon-back" circle @click="back"></el-button>
        <el-button icon="el-icon-circle-plus" circle v-if="readonlyMode === true" @click="startEdit()"></el-button>
    </div>
    <el-form :model="memberForm" ref="memberForm" :disabled="readonlyMode">
      <el-form-item label="Person Name"  prop="name" label-width="120px">
          <el-input v-model="memberForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Address"  prop="address" label-width="120px">
          <el-input type="textarea" v-model="memberForm.address"></el-input>
         </el-form-item>
         <el-form-item label="Phone"  prop="phone" label-width="120px">
          <el-input v-model="memberForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Email"  prop="email" label-width="120px">
          <el-input v-model="memberForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Fax"  prop="fax" label-width="120px">
          <el-input v-model="memberForm.fax" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Member Class" prop="classes" label-width="120px">
          <el-select v-model="memberForm.classes" placeholder="Select" filterable>
            <el-option
              v-for="item in allClasses"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
      </el-form-item>
    </el-form>

    <div>
      <el-button v-if="readonlyMode === false" type="primary" @click="submitBaseForm('memberForm')">{{ memberForm.id? 'Update' : 'Create' }}</el-button>
      <el-button @click="resetForm('memberForm')">Reset</el-button>
    </div>

  </div>
</template>
<script lang="ts">
import axios from '@/axios'
import { Component, Vue } from 'vue-property-decorator'
import moment from 'moment'

@Component
export default class MemberDetail extends Vue {
  memberForm: any = {}
  readonlyMode: any = false

  allClasses: any = []


  created() {
    this.getAllClass()
    if (Number(this.$route.params.id)) {
      this.memberForm.id = Number(this.$route.params.id)
      this.getMemberDetail()
    }
  }

  getAllClass() {
    axios.get('/base/member/class/getAll').then(
      (res: any) => {
        this.allClasses = res.data.data
      }
    )
  }

  getMemberDetail() {
    axios.get(`/base/member/${this.$route.params.id}`).then(res => {
      this.readonlyMode = true
      this.memberForm = res.data.data
    })
  }

  back() {
    this.$router.push({ path: '/base/member' })
  }

  startEdit() {
    this.readonlyMode = false
  }

  submitBaseForm(formName: string) {
    const validData: any = this.$refs[formName]
    validData.validate((valid: any) => {
            if (valid) {
                console.log(this.memberForm)
                axios.post('/base/member/' + (this.memberForm.id ? 'update' : 'create'), this.memberForm)
                    .then((res: any) => {
                        this.back()
                        this.$notify({
                            title: 'Msg',
                            showClose: true,
                            message: 'Action is successful ',
                            type: 'success',
                        })
                    this.back()
                })
            } else {
                return false
            }
      }
    )
  }

   resetForm(formName: string) {
    const ref: any = this.$refs[formName]
    ref.resetFields()
    this.memberForm = {}
     if (this.$route.params.id) {
      this.getMemberDetail()
    }
  }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>
