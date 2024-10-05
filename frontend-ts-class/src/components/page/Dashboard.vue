<template>
    <div>
        <el-row :gutter="24">
            <el-col :span="8">
                <v-card
                    max-width="500"
                >
                    <div class="user-card">
                        <div class="user-info-list-1">
                            <span  style="margin: 0px;">Felix</span>
                        </div>
                        <div>
                            FixedAsset - Springboot MVC X VUE 2.0
                        </div>
                        <br>
                        <br>
                        <div>
                            Testing any charts
                        </div> 
                    </div> 
                </v-card>
            </el-col>
            <el-col :span="16">
                <v-card
                    max-width="1200"
                >
                    <div class="card-title">
                        Sell Total Per Year & Week
                    </div>
                    <div class="card-content">
                        <ChartJs v-bind="chartsSetA" /> 
                    </div> 
                </v-card>
            </el-col>
        </el-row>
        <el-row :gutter="24">
            <el-col :span="12">
                <v-card
                    max-width="1000"
                >
                    <div class="card-title">
                        Sell Item Total Per Year & Week
                    </div>
                    <div class="card-content">
                        <ChartJs v-bind="chartsSetB" /> 
                    </div> 
                </v-card> 
            </el-col>
            <el-col :span="12">
                <v-card
                    max-width="1000"
                >
                    <div class="card-title">
                        Sell Total Per Year & Week
                    </div>
                    <div class="card-content">
                        <ChartJs v-bind="chartsSetC" /> 
                    </div> 
                </v-card> 
            </el-col>
        </el-row>
        <el-row :gutter="24">
            <el-col :span="12">
                <v-card
                    max-width="1000"
                >
                    <div class="card-title">
                        Sell Total Per Year & Week
                    </div>
                    <div class="card-content">
                        <ChartJs v-bind="chartsSetD" /> 
                    </div> 
                </v-card> 
            </el-col>
        </el-row>
    </div>
</template>

<script lang="ts">
import bus from '@/components/common/bus'
import axios from '@/axios'
import { Component, Vue } from 'vue-property-decorator'
import ApexChartOne from '@/components/charts/apex/apexOne.vue'
import ChartJs from '@/components/charts/chartJs/index.vue'

@Component({
    components: {
        ApexChartOne,
        ChartJs
    }
})
export default class Dashboard extends Vue {

    groupByFind: any = {}


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
            label: 'Total Inov',
            colors: '#a1d41b'
        }
    }

    created() {
        this.queryTotalYearWeek()
        this.queryCountYearWeek()
        this.queryTotalShop()
        this.queryCountShop()
    }

    queryTotalYearWeek() {
        axios.get(
            '/invoice/queryTotalYearWeek'
        ).then(
            (res: any) => {
                this.queryTotalYearWeekList = res.data.data
            }
        )
    } 

    queryCountYearWeek() {
        axios.get(
            '/invoice/queryCountYearWeek'
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


<style scoped>
.card-title{
    padding: 0.5rem 0rem 0rem 0.5rem;
}

.card-content-score {
    padding-right: 0.8rem;
    padding-left: 0.8rem;
    padding-bottom: 0.5rem;
    padding-top:0.7rem;
}

.card-content{
    padding-right: 0.8rem;
    padding-left: 0.8rem;
    padding-bottom: 0.5rem;
}

.el-row {
    margin-bottom: 20px;
}

.grid-content {
    display: flex;
    align-items: center;
    height: 100px;
}

.grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
}

.grid-num {
    font-size: 30px;
    font-weight: bold;
}

.grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
}

.grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
}

.user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
}

.user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
}

.user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
}

.user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
}

.user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
}

.user-card {
    padding: 30px 30px 30px 30px;
}

.user-info-list span {
    margin-left: 70px;
}

.mgb20 {
    margin-bottom: 1px;
}

.mgb20-score {
    /* margin-bottom: 20px; */
    background-color: lightskyblue;
    border-color: midnightblue;
    border-width: 0.2rem;
    height: 220px;
    width: 260px;
}

.todo-item {
    font-size: 14px;
}

.todo-item-del {
    text-decoration: line-through;
    color: #999;
}

.schart {
    width: 100%;
    height: 300px;
}
</style>
