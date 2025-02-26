<template>
    <div class="container">
        <div class="handle-box">
            <el-form :inline="true">
                <el-form-item>
                    <el-input
                            v-model="searchForm.name"
                            placeholder="Search"
                            clearable
                    >
                    </el-input>
                </el-form-item>

            <!--   <el-form-item>
                    <el-button @click="downloadTemplateExcel()">Download Template Excel</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button @click="clickUploadDialog">Upload Excel</el-button>
                </el-form-item>--> 

                <el-form-item>
                    <el-button @click="allList()">Find</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="goToCreated()">Create</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-table
                ref="multipleTable"
                :data="tableData"
                tooltip-effect="dark"
                style="width: 100%"
                :default-sort = "{prop: 'placeCode', order: 'descending'}"
                @selection-change="handleSelectionChange">
            <el-table-column
                sortable
                prop="promotionCode"
              label="Promotion Code">
            </el-table-column>
            <el-table-column
              prop="promotionName"
              label="Promotion Name">
            </el-table-column>
            <el-table-column
              prop="periodStart"
              label="Period Start">
            </el-table-column>
            <el-table-column
              prop="periodEnd"
              label="Period End">
            </el-table-column>
            <el-table-column
                    prop="created"
                    width="200"
                    label="Created At"
            >
            </el-table-column>
            <el-table-column
                    prop="updated"
                    width="200"
                    label="Update At"
            >
            </el-table-column>
            <el-table-column
                    prop="icon"
                    width="260px"
                    label="Action">

                <template slot-scope="scope">
                    <el-button size="mini" @click="editHandle(scope.row.id)">Edit</el-button>
                    <el-divider direction="vertical"></el-divider>
                    <el-button size="mini" type="danger" @click="delItem(scope.row.id)">Delete</el-button>
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
                    </el-upload>
        </el-dialog>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import axios from '@/axios'
import { downloadTempExcelFile, formatJson, readExcel } from '@/utils/importExcel'
import moment from 'moment'
import { formatData } from '@/views/product/excelHeaders'

@Component
export default class Location extends Vue {
    fileList: any = []
    editForm: any = {}
    tableData: any = []
    searchForm: any = {
        page: 1,
        limit: 10
    }
    multipleSelection: any = []

    size: number|undefined
    current: number = 1
    total: number = 0

    delBtlStatu: boolean = true
    dialogVisible: boolean = false
    uploaderDialog: boolean = false

    editFormRules = {
        placeCode: [
            { required: true, message: 'Place Code cannot blank!', trigger: 'blur' }
        ],
        placeName: [
            { required: true, message: 'Place Name cannot blank!', trigger: 'blur' }
        ]
    }

    testEcelHeader1 = [
        'Place Code',
        'Place Name',
        'Place Other Name',
        'Country',
        'Address',
        'ZIP Code',
        'Remark'
    ]
    testEcelHeader2 = [
        'placeCode',
        'placeName',
        'placeOtherName',
        'country',
        'address',
        'zipCode',
        'remark'
    ]

    created() {
        this.allList() 
    }

    clearFile() {
        this.fileList = []
    }
    clickUploadDialog() {
        this.uploaderDialog = true
    }
    closerUploadDialog() {
        this.uploaderDialog = false
    }

    downloadTemplateExcel() {
        downloadTempExcelFile(this.testEcelHeader1, 'location_template.xlsx')
    }

  async uploadFile(file: any) {
        const data = await readExcel(file)
        const reData = formatJson(this.testEcelHeader1, this.testEcelHeader2, data)

        axios.post('/base/location/batch-create', reData).then((res: any) => {
            if (res) {
                this.$notify({
                    title: 'Msg',
                    showClose: true,
                    message: 'Upload success',
                    type: 'success',
                })
                this.uploaderDialog = false
                this.allList() 
                this.fileList = []
                file = undefined
            }
        })
    }

    allList() {
        axios.post(
            '/base/promotion/list',
            this.searchForm
        ).then(
            (res: any) => {
                this.tableData = res.data.data.records
                this.size = res.data.data.size
                this.current = res.data.data.current
                this.total = res.data.data.total

                this.tableData.forEach((re: any) => {
                    const newPeriodStart =  re.periodStart ? moment(new Date(re.periodStart)).format('DD-MM-YYYY HH:MM') : null
                    const newPeriodEnd =  re.periodStart ? moment(new Date(re.periodEnd)).format('DD-MM-YYYY HH:MM') : null

                    const newCreated =  re.created ? moment(new Date(re.created)).format('DD-MM-YYYY HH:MM') : null
                    const newUpdated =  re.updated ? moment(new Date(re.updated)).format('DD-MM-YYYY HH:MM') : null

                    re['created'] = newCreated
                    re['updated'] = newUpdated
                    re['periodStart'] = newPeriodStart
                    re['periodEnd'] = newPeriodEnd
                    return re
                })
            }
        )
    }

    toggleSelection(rows: any) {
        if (rows) {
                rows.forEach((row: any) => {
                const multipleTable: any = this.$refs.multipleTable
                multipleTable.toggleRowSelection(row);
            })
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
        this.allList() 
    }

    handleCurrentChange(val: number) {
        this.searchForm.page = val
        this.allList() 
    }

    resetForm(formName: string) {
        const refs: any = this.$refs[formName]
        refs.resetFields();
        this.dialogVisible = false
        this.editForm = {
            id: 0,
            placeName: '',
            placeCode: '',
            remark: '',
            placeOtherName: null
        }
    }

    handleClose() {
        this.resetForm('editForm')
    }

    editHandle(id: number) {
        this.$router.push({ path: `/promotion/detail/${id}` })
    }

    delItem(id: number) {
        axios.delete('/base/promotion/void/'+ id).then(res => {
            this.allList() 
            this.$notify({
                title: '',
                showClose: true,
                message: 'Action is successful ',
                type: 'success'
            });
        })
    }

    goToCreated() {
        this.$router.push('/promotion/create')
    }

}
</script>

<style scoped>

    .handle-box {
        margin-bottom: 20px;
    }

</style>