<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true">
                <el-form-item>
                    <el-input
                      v-model="searchForm.actionName"
                      placeholder="Action Name"
                      clearable
                    >
                    </el-input>
                </el-form-item>

                <el-form-item>
                    <el-button @click="stockTakeList">Find</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="dialogVisible = true">Create</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                style="width: 100%"
                @selection-change="handleSelectionChange">
            <el-table-column
              prop="name"
              label="Name"
              width="200">
            </el-table-column>
            <el-table-column
                    prop="statu"
                    label="Status">
                <template slot-scope="scope">
                    <el-tag size="small" v-if="scope.row.status === 1" type="success">In Progress</el-tag>
                    <el-tag size="small" v-else-if="scope.row.status=== 2" type="danger">Finished</el-tag>
                    <el-tag size="small" v-else-if="scope.row.status=== 0" type="danger">Deleted</el-tag>
                </template>

            </el-table-column>
            <el-table-column
              prop="placeCode"
              label="Place Code"
            >
            </el-table-column>
            <el-table-column
              prop="placeName"
              label="Place Name"
            >
            </el-table-column>
            <el-table-column
              prop="startTime"
              label="Start Time"
            >
            </el-table-column>
            <el-table-column
              prop="endTime"
              label="End At"
            >
            </el-table-column>
            <el-table-column
                    prop="icon"
                    width="320px"
                    label="Action">

                <template slot-scope="scope">
                    <el-button
                      size="mini"
                      v-if="scope.row.status === 1"
                      @click="toDetailPage(scope.row.id)">Detail</el-button>
                    <el-button
                      size="mini"
                      type="danger"
                      v-if="scope.row.status === 1"
                      @click="finishItem(scope.row.id)">Finish</el-button>
                    <el-button
                      v-if="scope.row.status === 1"
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
                title="Form Box"
                :visible.sync="dialogVisible"
                width="700px"
                :before-close="handleClose">

            <el-form :model="editForm" :rules="editFormRules" ref="editForm">
                <el-form-item label="Name"  prop="name" label-width="100px">
                    <el-input v-model="editForm.name" autocomplete="off"></el-input>
                </el-form-item>

                <el-form-item label="Place" prop="locationId" label-width="100px">
                    <el-select v-model="editForm.locationId" placeholder="Select" filterable>
                        <el-option
                        v-for="item in placeItem"
                        :key="item.id"
                        :label="item.placeName"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>


            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm('editForm')">Cancel</el-button>
                <el-button type="primary" @click="submitForm('editForm')">{{ editForm.id? 'Update' : 'Create' }}</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script lang="ts">
import axios from '@/axios'
import moment from 'moment'
import { Component, Vue } from 'vue-property-decorator'

@Component
export default class Stocktake extends Vue {
    searchForm: any = {
                limit: 10,
                page: 1
    }
    editForm: any = {}

    delBtlStatu: boolean = true
    sumTotal: number = 0
    total: number = 0
    size: number|undefined
    current: number = 1
    dialogVisible: boolean = false

                tableData: any =[]
                placeItem: any =[]
                typeItem: any =[]
                deptItem: any =[]

                editFormRules = {
                    actionName: [
                        {required: true, message: 'Action Nmae cannot blank!', trigger: 'blur'}
                    ],
                    actionPlace: [
                        {required: true, message: 'Action Place must choose!', trigger: 'blur'}
                    ]
                }
                roleDialogFormVisible: boolean = false
                defaultProps = {
                    children: 'children',
                    label: 'name'
                }
                treeCheckedKeys: any = []
                checkStrictly: boolean = true
                multipleSelection: any = []

        created() {
            this.stockTakeList()
            this.getAllPlace()
        }

        stockTakeList() {
                axios.post(
                    '/stockTake/list',
                    this.searchForm
                ).then(
                    (res: any) => {
                    this.tableData = res.data.data.records
                    this.size = res.data.data.size
                    this.current = res.data.data.current
                    this.total = res.data.data.total

                    this.tableData.forEach((re: any) => {
                        const newEndTime =  re.endTime ? moment(new Date(re.endTime)).format('DD-MM-YYYY HH:MM') : null
                        const newStartTime =  re.startTime ? moment(new Date(re.startTime)).format('DD-MM-YYYY HH:MM') : null

                        re['endTime'] = newEndTime
                        re['startTime'] = newStartTime
                        return re
                    })
                })
          }

          getAllPlace() {
                axios.get(
                    '/base/location/getAll'
                ).then(
                    (res: any) => {
                        // console.log(res.data.data)
                        this.placeItem = res.data.data
                    }
                )
            }

            toggleSelection(rows: any) {
                if (rows) {
                    rows.forEach((row: any) => {
                        const multipleTable: any = this.$refs.multipleTable
                        multipleTable.toggleRowSelection(row);
                    });
                } else {
                    const multipleTable: any = this.$refs.multipleTable
                    multipleTable.clearSelection();
                }
            }

            handleSelectionChange(val: any) {
                this.multipleSelection = val;

                this.delBtlStatu = val.length == 0
            }

            handleSizeChange(val: number) {
                this.searchForm.limit = val
                this.stockTakeList()
            }

            handleCurrentChange(val: number) {
                this.searchForm.page = val
                this.stockTakeList()
            }

            resetForm(formName: string) {
                const refs: any = this.$refs[formName]
                refs.resetFields();
                this.dialogVisible = false
                this.editForm = {}
            }

            handleClose() {
                this.resetForm('editForm')
            }

            submitForm(formName: string) {
                const refs: any = this.$refs[formName]
                refs.validate((valid: any) => {
                    if (valid) {
                      console.log(this.editForm)
                        axios.post('/stockTake/create', this.editForm)
                            .then((res: any) => {
                                this.stockTakeList()
                                this.$notify({
                                    title: '',
                                    showClose: true,
                                    message: 'Action is successful ',
                                    type: 'success',
                                });

                                this.dialogVisible = false
                                this.handleClose()
                            })
                    } else {
                        return false;
                    }
                });
            }

    finishItem(id: number) {
                axios.delete(`/stockTake/finish/${id}`).then(res => {
                    this.stockTakeList()
                    this.$notify({
                        title: '',
                        showClose: true,
                        message: 'Action is successful ',
                        type: 'success'
                    });
                })
    }

    delItem(id: number) {
        axios.delete(`/stockTake/remove/${id}`).then(res => {
            this.stockTakeList()
            this.$notify({
                title: '',
                showClose: true,
                message: 'Action is successful ',
                type: 'success'
            })
        })
    }

    toDetailPage(id: number) {
        this.$router.push({ path: `/stock/stocktake/${id}` })
    }
}
</script>
<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }
</style>