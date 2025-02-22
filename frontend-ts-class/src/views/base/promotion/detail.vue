<template>
    <div class="w-full bg-white p-1 shadow-lg rounded-lg">
        <div class="handle-box">
            <div class="handle-box p-1">
                <el-button icon="el-icon-back" circle @click="back"></el-button>
                <el-button icon="el-icon-circle-plus" circle v-if="readonlyMode === true" @click="startEdit()"></el-button>
            </div>
        </div>
        <el-form :model="editForm" :disabled="readonlyMode" class="grid lg:grid-cols-4 gap-3 px-6">
            <el-form-item label="Promotion Code"  prop="promotionCode" label-width="120px">
                <el-input v-model="editForm.promotionCode" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Promotion Name"  prop="promotionName" label-width="120px" class="lg:col-span-3">
                <el-input v-model="editForm.promotionName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Description"  prop="description" label-width="120px" class="lg:col-span-4">
                <el-input type="textarea" v-model="editForm.description" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Period Start"  prop="description" label-width="120px">
                <el-date-picker
                    v-model="editForm.periodStart"
                    type="datetime"
                    placeholder="Period Start">
                  </el-date-picker>
            </el-form-item>
            <el-form-item label="Period End"  prop="description" label-width="120px">
                <el-date-picker
                    v-model="editForm.periodEnd"
                    type="datetime"
                    placeholder="Period End">
                  </el-date-picker>
            </el-form-item>
            <el-form-item label="Online" prop="online" label-width="120px">
                <el-select v-model="editForm.online" placeholder="Select" filterable>
                    <el-option
                        v-for="list in yesOrNoLists"
                        :key="list.value"
                        :label="list.label"
                        :value="list.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="In Store" prop="inStore" label-width="120px">
                <el-select v-model="editForm.inStore" placeholder="Select" filterable>
                    <el-option
                        v-for="list in yesOrNoLists"
                        :key="list.value"
                        :label="list.label"
                        :value="list.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="Member Only" prop="member" label-width="120px">
                <el-select v-model="editForm.member" placeholder="Select" filterable>
                    <el-option
                        v-for="list in yesOrNoLists"
                        :key="list.value"
                        :label="list.label"
                        :value="list.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="All to use one discount" prop="allOneDiscount" label-width="180px">
                <el-select v-model="editForm.allOneDiscount" placeholder="Select" filterable>
                    <el-option
                        v-for="list in yesOrNoLists"
                        :key="list.value"
                        :label="list.label"
                        :value="list.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="After Before Tax" prop="afterBeforeTax" label-width="150px">
                <el-select v-model="editForm.afterBeforeTax" placeholder="Select" filterable>
                    <el-option
                        v-for="list in afterOrBeforeTaxLists"
                        :key="list.value"
                        :label="list.label"
                        :value="list.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="Coupon Requested" prop="couponRequest" label-width="180px">
                <el-select v-model="editForm.couponRequest" placeholder="Select" filterable>
                    <el-option
                        v-for="list in yesOrNoLists"
                        :key="list.value"
                        :label="list.label"
                        :value="list.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="Discount"  prop="discount" label-width="130px">
              <el-input-number v-model="editForm.discount" :step="1" class="w-full" ></el-input-number>
            </el-form-item>
            <el-form-item label="Discount Type"  prop="discount" label-width="130px">
                <el-select v-model="editForm.discountType" placeholder="Select" filterable :disabled="editForm.allOneDiscount">
                <el-option
                    v-for="discount in discountList"
                    :key="discount.type"
                    :label="discount.type"
                    :value="discount.type">
                </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="Promotion Type"  prop="discount" label-width="130px">
                <el-select v-model="editForm.promotionType" placeholder="Select" filterable>
                <el-option
                    v-for="promotionType in promotionTypeLists"
                    :key="promotionType.catelogCode"
                    :label="promotionType.catelogName"
                    :value="promotionType.catelogCode">
                </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="Coupon Main Code"  prop="couponMainCode" label-width="150px" :disabled="editForm.couponRequest === false || editForm.couponRequest === null">
                <el-input v-model="editForm.couponMainCode" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Remark"  prop="Remark" label-width="120px" class="lg:col-span-4">
                <el-input type="textarea" v-model="editForm.remark" autocomplete="off"></el-input>
            </el-form-item>
            <div class="lg:col-span-4">
                <h2 class="px-6">Discount by store</h2>
                <el-table
                    class="px-5"
                    ref="multipleTable"
                    :data="editForm.promotionLocationItems"
                    tooltip-effect="dark"
                    id="tab1"
                >
                    <el-table-column 
                        label="Store" 
                        prop="place">
                            <template slot-scope="scope">
                                <el-select v-model="scope.row.placeId" placeholder="Select" filterable class="w-[100%]">
                                    <el-option
                                        v-for="placeItems in placeLists"
                                        :key="placeItems.id"
                                        :label="placeItems.placeName"
                                        :value="placeItems.id">
                                    </el-option>
                                </el-select>
                            </template>
                    </el-table-column>        
                    <el-table-column 
                        label="Promotion Code" 
                        prop="promotionCode">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.promotionCode" autocomplete="off"></el-input>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Promotion Name" 
                        prop="promotionName">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.promotionName" autocomplete="off"></el-input>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Discount Amount" 
                        prop="discountAmount">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.discountAmount" :step="1" class="w-full" ></el-input-number>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Discount Type" 
                        prop="discountType">
                            <template slot-scope="scope">
                                <el-select v-model="editForm.discountType" placeholder="Select" filterable :disabled="scope.row.discountType">
                                    <el-option
                                        v-for="discount in discountList"
                                        :key="discount.type"
                                        :label="discount.type"
                                        :value="discount.type">
                                    </el-option>
                                </el-select>
                            </template>
                    </el-table-column>
                    <el-table-column
                        label="Action" 
                        prop="action"
                    >
                        <template slot-scope="scope">
                            <el-button type="danger" @click.prevent="promotionLocationItemsRowRemove(scope.row, scope.$index)">Delete</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="lg:col-span-1"></div>
            <el-button @click.prevent="editForm.promotionLocationItems.push({ placeId: null, promotionCode: '', promotionName: '' })" class="lg:col-span-2">
                + Add rows
            </el-button>
            <div class="lg:col-span-1"></div>

            <div class="lg:col-span-4">
                <h2 class="px-6">Discount by department</h2>
                <el-table
                    class="px-5"
                    ref="multipleTable"
                    :data="editForm.promotionDepartmentItems"
                    tooltip-effect="dark"
                    id="tab1"
                >
                    <el-table-column 
                        label="Department" 
                        prop="department">
                            <template slot-scope="scope">
                                <el-select v-model="scope.row.deptId" placeholder="Select" filterable class="w-[100%]">
                                    <el-option
                                        v-for="deptItems in deptLists"
                                        :key="deptItems.id"
                                        :label="deptItems.deptName"
                                        :value="deptItems.id">
                                    </el-option>
                                </el-select>
                            </template>
                    </el-table-column>        
                    <el-table-column 
                        label="Promotion Code" 
                        prop="promotionCode">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.promotionCode" autocomplete="off"></el-input>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Promotion Name" 
                        prop="promotionName">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.promotionName" autocomplete="off"></el-input>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Discount Amount" 
                        prop="discountAmount">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.discountAmount" :step="1" class="w-full" ></el-input-number>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Discount Type" 
                        prop="discountType">
                            <template slot-scope="scope">
                                <el-select v-model="editForm.discountType" placeholder="Select" filterable :disabled="scope.row.discountType">
                                    <el-option
                                        v-for="discount in discountList"
                                        :key="discount.type"
                                        :label="discount.type"
                                        :value="discount.type">
                                    </el-option>
                                </el-select>
                            </template>
                    </el-table-column>
                    <el-table-column
                        label="Action" 
                        prop="action"
                    >
                        <template slot-scope="scope">
                            <el-button type="danger" @click.prevent="promotionDepartmentItemsRowRemove(scope.row, scope.$index)">Delete</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="lg:col-span-1"></div>
            <el-button @click.prevent="editForm.promotionDepartmentItems.push({ deptId: null, promotionCode: '', promotionName: '' })" class="lg:col-span-2">
                + Add rows
            </el-button>
            <div class="lg:col-span-1"></div>


            <div class="lg:col-span-4">
                <h2 class="px-6">Discount by product type</h2>
                <el-table
                    class="px-5"
                    ref="multipleTable"
                    :data="editForm.promotionTypeItems"
                    tooltip-effect="dark"
                    id="tab1"
                >
                    <el-table-column 
                        label="Type" 
                        prop="type">
                            <template slot-scope="scope">
                                <el-select v-model="scope.row.typeId" placeholder="Select" filterable class="w-[100%]">
                                    <el-option
                                        v-for="typeItems in typeLists"
                                        :key="typeItems.id"
                                        :label="typeItems.typeName"
                                        :value="typeItems.id">
                                    </el-option>
                                </el-select>
                            </template>
                    </el-table-column>        
                    <el-table-column 
                        label="Promotion Code" 
                        prop="promotionCode">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.promotionCode" autocomplete="off"></el-input>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Promotion Name" 
                        prop="promotionName">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.promotionName" autocomplete="off"></el-input>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Discount Amount" 
                        prop="discountAmount">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.discountAmount" :step="1" class="w-full" ></el-input-number>
                            </template>
                    </el-table-column>
                    <el-table-column 
                        label="Discount Type" 
                        prop="discountType">
                            <template slot-scope="scope">
                                <el-select v-model="editForm.discountType" placeholder="Select" filterable :disabled="scope.row.discountType">
                                    <el-option
                                        v-for="discount in discountList"
                                        :key="discount.type"
                                        :label="discount.type"
                                        :value="discount.type">
                                    </el-option>
                                </el-select>
                            </template>
                    </el-table-column>
                    <el-table-column
                        label="Action" 
                        prop="action"
                    >
                        <template slot-scope="scope">
                            <el-button type="danger" @click.prevent="promotionTypeItemsRowRemove(scope.row, scope.$index)">Delete</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="lg:col-span-1"></div>
            <el-button @click.prevent="editForm.promotionTypeItems.push({ typeId: null, promotionCode: '', promotionName: '' })" class="lg:col-span-2">
                + Add rows
            </el-button>
            <div class="lg:col-span-1"></div>

            <div class="dialog-footer p-4">
                <el-button type="primary" @click="submitForm('editForm')">{{ editForm.id? 'Update' : 'Create' }}</el-button>
                <el-button @click="resetForm('editForm')">Cancel</el-button>
            </div>
        </el-form>
        
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

@Component
export default class PromotionDetail extends Vue {

    editForm: any = {
        promotionLocationItems: [],
        promotionDepartmentItems: [],
        promotionTypeItems: []

    }
    editFormRules: any = []
    readonlyMode: boolean = false
    hideSaveBtn: boolean = false

    discountList = [
        { type: '%' },
        { type: '$' },
    ]

    afterOrBeforeTaxLists = [
        {
            label: 'After Tax',
            value: 1
        },
        {
            label: 'Before Tax',
            value: 0
        }
    ]

    yesOrNoLists = [
    {
            label: 'Yes',
            value: 1
        },
        {
            label: 'No',
            value: 0
        }
    ]

    created() {
        this.loadPlaceList()
        this.loadDeptList()
        this.loadTypeList()
        this.loadPromotionTypeList()
        if (this.$route.params.id) {
            this.editForm.id = Number(this.$route.params.id)
            this.editHandle()      
        }
    }

    startEdit() {
        this.readonlyMode = false
    }

    back() {
        this.$router.push({ path: '/promotion' })
    }

    editHandle(){
        axios.get(`/base/promotion/${this.$route.params.id}`).then(
            (res: any) => {
                this.readonlyMode = true
                this.editForm = res.data.data
            }
        )
    }

    submitForm(formName: string) {

        if (this.editForm.id) {
            axios.post('/base/promotion/update', this.editForm)
            .then((res: any) => {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Action is successful ',
                    type: 'success',
                })
                this.editHandle()      
            })
        } else {
                const finalForm: any = this.editForm

                finalForm.newPromotionTypeItems = this.editForm.promotionTypeItems
                finalForm.newPromotionLocationItems = this.editForm.promotionLocationItems
                finalForm.newPromotionDepartmentItems = this.editForm.promotionDepartmentItems

                axios.post('/base/promotion/create', finalForm).then((res: any) => {
                    this.$notify({
                        title: 'Msg',
                        showClose: true,
                        message: 'Action is successful ',
                        type: 'success',
                    })
                    this.editHandle()
                })
        }

    }

    resetForm(formName: string) {
        this.editForm = {
            promotionLocationItems: [],
            promotionDepartmentItems: [],
            promotionTypeItems: []
        }

        this.editHandle()
    }

    placeLists: any = []
    loadPlaceList() {
        axios.get(
            '/base/location/getAll'
        ).then(
        (res: any) => {
            this.placeLists = res.data.data
        })
    }

    deptLists: any = []
    loadDeptList() {
        axios.get(
            '/base/department/getAll'
        ).then(
        (res: any) => {
            this.deptLists = res.data.data
        })
    }

    typeLists: any = []
    loadTypeList() {
        axios.get(
            '/product/type/getAll'
        ).then(
        (res: any) => {
            this.typeLists = res.data.data
        })
    }

    promotionTypeLists: any = []
    loadPromotionTypeList() {
        axios.get(
            '/base/promotion-catelog/getAll'
        ).then(
        (res: any) => {
            this.promotionTypeLists = res.data.data
        })
    }

    promotionLocationItemsRowRemove(data: any, index: number) {
        if (data && data.id) {
            axios.delete(
                `/base/promotion/location/void/${data.id}`
            ).then(res => {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Action is successful ',
                    type: 'success',
                })
                this.editForm.promotionLocationItems.splice(index, 1)
            })
            
        } else {
            this.editForm.promotionLocationItems.splice(index, 1)
        }
    }

    promotionDepartmentItemsRowRemove(data: any, index: number) {
        if (data && data.id) {
            axios.delete(
                `/base/promotion/department/void/${data.id}`
            ).then(res => {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Action is successful ',
                    type: 'success',
                })
                this.editForm.promotionDepartmentItems.splice(index, 1)
            })
        } else {
            this.editForm.promotionDepartmentItems.splice(index, 1)
        }
    }

    promotionTypeItemsRowRemove(data: any, index: number) {
        if (!data.id === null) {
            axios.delete(
                `/base/promotion/type/void/${data.id}`
            ).then(res => {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Action is successful ',
                    type: 'success',
                })
                this.editForm.promotionTypeItems.splice(index, 1)
            })
        } else {
            this.editForm.promotionTypeItems.splice(index, 1)
        }
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