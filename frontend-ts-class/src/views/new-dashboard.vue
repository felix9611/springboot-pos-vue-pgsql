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
        <div class="lg:col-span-2 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Total Income & cost by Year-Month
            </div>
            <div class="p-1">
                <PlotlyChart v-bind="chartsSetA" /> 
            </div>
        </div>
        <div class="lg:col-span-2 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Difference of between of Income and cost by Year-Month
            </div>
            <div class="p-1">
                <PlotlyChart v-bind="chartsSetA1" /> 
            </div>
        </div>
        <div class="lg:col-span-2 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Sell Item Total Per Year & Months
            </div>
            <div class="p-1">
                <PlotlyChart v-bind="chartsSetB" /> 
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
            <PlotlyChart v-bind="chartsSetD" />
        </div>
        <div class="lg:col-span-full shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Top 10 Sell Total Price per Product
            </div>
            <PlotlyChart v-bind="chartsSetE" />
        </div>
        <div class="lg:col-span-full shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Top 10 Sell Qtys & In-stock Qtys By Product
            </div>
            <PlotlyChart v-bind="chartsSetK" />
        </div>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Top 10 Sell Total Price Per Product Type
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetG" />
            </div>
        </div>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Top 10 Sell Qtys Per Product Type
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetH" />
            </div>
        </div>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Top 10 Sell Total Price Per Department
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetI" />
            </div>
        </div>
        <div class="lg:col-span-1 shadow-lg rounded-lg bg-white">
            <div class="font-bold p-1">
                Top 10 Sell Qtys Per Department
            </div>
            <div class="p-1">
                <ChartJs v-bind="chartsSetJ" />
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
import PlotlyChart from '@/components/charts/plotyjs/index.vue'

@Component({
    components: {
        ChartJsStackedChart,
        ApexChartOne,
        ChartJs,
        PlotlyChart
    }
})
export default class Dashboard extends Vue {
    groupByFind: any = {}
    dates: any = []


    queryTotalYearWeekList: any = []
    queryCountYearWeekList: any = []
    queryTotalShopList: any = []
    queryCountShopList: any = []
    querySalesByProductList: any = [] 
    querySalesByProductCountList: any = [] 

    queryTotalSalesByTypeList: any = [] 
    queryCountSalesByTypeList: any = [] 
    queryCountSalesByDeptList: any = [] 
    queryTotalSalesByDeptList: any = [] 

    queryInStockQtysList: any = [] 

    get chartsSetA() {
        return {
            plotyData: [
                {
                    name: 'Total Income',
                    x: this.queryTotalYearWeekList.map((x: any) => x.yearMonth),
                    y: this.queryTotalYearWeekList.map((x: any) => x.income),
                    line: {shape: 'spline'},
                },
                {
                    name: 'Total Cost',
                    x: this.queryTotalYearWeekList.map((x: any) => x.yearMonth),
                    y: this.queryTotalYearWeekList.map((x: any) => x.costPrice),
                    line: {shape: 'spline'},
                }
            ],
            plotyLayout: {
                margin: {
                    l: 50,
                    r: 20,
                    b: 50,
                    t: 30,
                    pad: 4
                },
                legend: {orientation:"h", xanchor: 'center', x:0.5, y:1 },
                height: 300
            }
        }
    }

    get chartsSetA1() {
        return {
            plotyData: [
                {
                    name: 'Gap',
                    x: this.queryTotalYearWeekList.map((x: any) => x.yearMonth),
                    y: this.queryTotalYearWeekList.map((x: any) => x.difference),
                    line: {shape: 'spline'},
                }
            ],
            plotyLayout: {
                margin: {
                    l: 50,
                    r: 20,
                    b: 50,
                    t: 30,
                    pad: 4
                },
                legend: {orientation:"h", xanchor: 'center', x:0.5, y:1 },
                height: 300
            }
        }
    }

    get chartsSetB() {
        return {
            plotyData: [
                {
                    name: 'Counts',
                    x: this.queryCountYearWeekList.map((x: any) => x.yearMonth),
                    y: this.queryCountYearWeekList.map((x: any) => x.count),
                    line: {shape: 'spline'},
                }
            ],
            plotyLayout: {
                margin: {
                    l: 50,
                    r: 20,
                    b: 50,
                    t: 30,
                    pad: 4
                },
                legend: {orientation:"h", xanchor: 'center', x:0.5, y:1 },
                height: 300
            }
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
        const labels = this.queryCountShopList.map((x: any) => x.placeName)
        const yData = this.queryCountShopList.map((x: any) => x.count)
        return {
            plotyData: [
                {
                    name: 'Total Qtys',
                    x: labels,
                    y: yData,
                    type: 'bar',
                }
            ],
            plotyLayout: {
                margin: {
                    l: 50,
                    r: 50,
                    b: 100,
                    t: 30,
                    pad: 4
                },
                legend: {orientation:"h", xanchor: 'center', x:0.5, y:1 },
                height: 300
            }
        }
    }

    get chartsSetE() {
        const labels = this.querySalesByProductList.map((x: any) => x.productName)
        const totalPrice = this.querySalesByProductList.map((x: any) => x.totalPrice)
        return {
            plotyData: [
                {
                    x: labels,
                    y: totalPrice,
                    type: 'bar',
                    name: 'Total Price($)',
                    hovertemplate: 'Total Price: %{y:$.2f}<extra></extra>',
                }
            ],
            plotyLayout: {
                margin: {
                    l: 50,
                    r: 50,
                    b: 100,
                    t: 30,
                    pad: 4
                },
                legend: {orientation:"h", xanchor: 'center', x:0.5, y:1 },
                height: 300
            }
         /*   width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'productName',
            value: 'totalPrice',
            data: this.querySalesByProductList,
            label: 'Total Price($)',
            colors: '#a1d41b'
        */
        }
    }

    get chartsSetF() {
        return {
            width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'productName',
            value: 'counts',
            data: this.querySalesByProductCountList,
            label: 'Total Qtys',
            colors: '#a1d41b'
        }
    }

    get chartsSetG() {
        return {
            width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'typeName',
            value: 'totalPrice',
            data: this.queryTotalSalesByTypeList,
            label: 'Total Price($)',
            colors: '#a1d41b'
        }
    }

    get chartsSetH() {
        return {
            width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'typeName',
            value: 'counts',
            data: this.queryCountSalesByTypeList,
            label: 'Total Qtys',
            colors: '#a1d41b'
        }
    }

    get chartsSetI() {
        return {
            width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'deptName',
            value: 'counts',
            data: this.queryCountSalesByDeptList,
            label: 'Total Qtys',
            colors: '#a1d41b'
        }
    }

    get chartsSetJ() {
        return {
            width: 1000,
            heigh: 90,
            type: 'bar',
            datasetKey: 'deptName',
            value: 'totalPrice',
            data: this.queryTotalSalesByDeptList,
            label: 'Total Price($)',
            colors: '#a1d41b'
        }
    }


    get chartsSetK() {
   
        const queryInStockQtysList = this.queryInStockQtysList
        const querySalesByProductCountList = this.querySalesByProductCountList
        const tempData = [...queryInStockQtysList, ...querySalesByProductCountList]

        const mergedData = Array.from(
            tempData.reduce((map, item) => {
                const key = item.productName;
                const existingItem = map.get(key) || {};
                
                // Merge properties dynamically
                map.set(key, {
                    ...existingItem,
                    ...item,
                    productName: key, // Ensure productName is retained
                    inStockQtys: item.inStockQtys || existingItem.inStockQtys || 0,
                    counts: item.counts || existingItem.counts || 0
                });
                
                return map;
            }, new Map()).values()
        );

        const labels = mergedData.map((x: any) => x.productName)
        const inStockQtys = mergedData.map((x: any) => x.inStockQtys)
        const saleQtys = mergedData.map((x: any) => x.counts)

        return {
            plotyData: [
                {
                    x: labels,
                    y: inStockQtys,
                    type: 'bar',
                    name: 'In Stock Qtys'
                },
                {
                    x: labels,
                    y: saleQtys,
                    type: 'bar',
                    name: 'Sales Qtys'

                }
            ],
            plotyLayout: {
                margin: {
                    l: 50,
                    r: 50,
                    b: 100,
                    t: 30,
                    pad: 4
                },
                legend: {orientation:"h", xanchor: 'center', x:0.5, y:1 },
                height: 300
            }
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
        this.querySalesByProduct()
        this.querySalesByProductCounts()
        this.queryTotalSalesByType()
        this.queryCountSalesByType()
        this.queryCountSalesByDept()
        this.queryTotalSalesByDept()
    }

    created() {
        this.queryTotalYearWeek()
        this.queryCountYearWeek()
        this.queryTotalShop()
        this.queryCountShop()
        this.querySalesByProduct()
        this.querySalesByProductCounts()

        this.queryTotalSalesByType()
        this.queryCountSalesByType()
        this.queryCountSalesByDept()
        this.queryTotalSalesByDept()
        this.queryInStockQtys()

        
    }

    queryInStockQtys() {
        axios.get('/product/location/queryInStockQtys').then(
            (res: any) => {
                this.queryInStockQtysList = res.data.data
            }
        )
    }

    queryTotalSalesByDept() {
        axios.post('/invoice/queryTotalSalesByDept', this.groupByFind).then(
            (res: any) => {
                this.queryTotalSalesByDeptList = res.data.data
            }
        )
    }

    queryCountSalesByDept() {
        axios.post('/invoice/queryCountSalesByDept', this.groupByFind).then(
            (res: any) => {
                this.queryCountSalesByDeptList = res.data.data
            }
        )
    }

    queryCountSalesByType() {
        axios.post('/invoice/queryCountSalesByType', this.groupByFind).then(
            (res: any) => {
                this.queryCountSalesByTypeList = res.data.data
            }
        )
    }

    queryTotalSalesByType() {
        axios.post('/invoice/queryTotalSalesByType', this.groupByFind).then(
            (res: any) => {
                this.queryTotalSalesByTypeList = res.data.data
            }
        )
    }

    querySalesByProductCounts() {
        axios.post('/invoice/querySalesByProductCounts', this.groupByFind).then(
            (res: any) => {
                this.querySalesByProductCountList = res.data.data
            }
        )
    }

    querySalesByProduct() {
        axios.post('/invoice/querySalesByProduct', this.groupByFind).then(
            (res: any) => {
                this.querySalesByProductList = res.data.data
            }
        )
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