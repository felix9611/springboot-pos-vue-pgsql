<template>
    <div class="p-4 flex grid-cols-1 grid md:grid-cols-2 lg:grid-cols-2 gap-3">
        <el-row :gutter="30" class="shadow-lg rounded-lg bg-white pt-6 md:col-span-full lg:col-span-full">
            <el-form :model="groupByFind">
                    <el-col :span="30">
                        <el-form-item label="Buy Date" prop="Dept" label-width="100px">
                            <el-date-picker
                            v-model="dates"
                            type="daterange"
                            placeholder="Select date and time">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="30">
                        <el-form-item>
                            <el-button type="primary" @click="goToFind()">Find</el-button>
                        </el-form-item>
                    </el-col>
            </el-form>
        </el-row>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Total Income by Year-Month
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetA" /> 
            </div>
        </div>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Sell Item Total Per Year & Week
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetB" /> 
            </div>
        </div>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Sell Total Income Per Shop
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetC" /> 
            </div>
        </div>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Sell Total Per Shop
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetD" />
            </div>
        </div>
    </div>
</template>
<script lang="ts">
import bus from '../components/common/bus'
import axios from '../axios'
import { Component, Vue } from 'vue-property-decorator'
import ApexChartOne from '@/components/charts/apex/apexOne.vue'
import ChartJsStackedChart from '@/components/charts/chartJs/StackedChart.vue'
import ChartJs from '@/components/charts/chartJs/index.vue'

@Component({
    components: {
        ChartJsStackedChart,
        ApexChartOne,
        ChartJs
    }
})
export default class Dashboard extends Vue {
    groupByFind: any = {}
    dates: any = []


    queryTotalYearWeekList: any = []
    queryCountYearWeekList: any = []
    queryTotalShopList: any = []
    queryCountShopList: any = []

    get chartsSetA() {
        return {
            width: 1000,
            heigh: 90,
            type: 'line',
            datasetKey: 'yearWeek',
            value: 'total',
            data: this.queryTotalYearWeekList,
            label: 'Total($)',
            colors: '#a1d41b'
        }
    }

    get getChartsSetAHeader() {
        const header: any = []
        const test = this.queryTotalYearWeekList.map(x=> {  
            return x.yearMonth
        })
        const xu = [ ...new Set(test) ]
        xu.forEach(r => {
            header.push({
              key: 'count',
              label: r,
              test: `return row.yearMonth == '${r}'`,
            })
        })
        return header
    }

    get chartsSetB() {
        return {
            width: 1000,
            heigh: 90,
            type: 'line',
            datasetKey: 'yearWeek',
            value: 'count',
            data: this.queryCountYearWeekList,
            label: 'Total Items',
            colors: '#a1d41b'
        }
    }

    get chartsSetC() {
        return {
            width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'placeName',
            value: 'total',
            data: this.queryTotalShopList,
            label: 'Total($)',
            colors: '#a1d41b'
        }
    }

    get chartsSetD() {
        return {
            width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'placeName',
            value: 'count',
            data: this.queryCountShopList,
            label: 'Total Items',
            colors: '#a1d41b'
        }
    }

    goToFind() {
        const [from, to] = this.dates
        this.groupByFind = {
            ...this.groupByFind,
            dateFrom: from,
            dateTo: to
        }

        this.queryTotalYearWeek()
        this.queryCountYearWeek()
        this.queryTotalShop()
        this.queryCountShop()
    }

    created() {
        this.queryTotalYearWeek()
        this.queryCountYearWeek()
        this.queryTotalShop()
        this.queryCountShop()
    }

    queryTotalYearWeek() {
        axios.post(
            '/invoice/queryTotalYearWeek', this.groupByFind
        ).then(
            (res: any) => {
                this.queryTotalYearWeekList = res.data.data
            }
        )
    } 

    queryCountYearWeek() {
        axios.post(
            '/invoice/queryCountYearWeek', this.groupByFind
        ).then(
            (res: any) => {
                this.queryCountYearWeekList = res.data.data
            }
        )
    }

    queryTotalShop() {
        axios.get(
            '/invoice/queryTotalShop'
        ).then(
            (res: any) => {
                this.queryTotalShopList = res.data.data
            }
        )
    }

    queryCountShop() {
        axios.get(
            '/invoice/queryCountShop'
        ).then(
            (res: any) => {
                this.queryCountShopList = res.data.data
            }
        )
    }

    name = localStorage.getItem('ms_username')

    get role() {
        return 'Felix'
    }


}
</script>