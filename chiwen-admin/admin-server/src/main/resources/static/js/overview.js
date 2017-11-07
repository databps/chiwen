$(function(){
	// 关闭菜单
    $(document).on("click", function(){
        $(window.parent.document).find('.onemenu').slideUp();
        $(window.parent.document).find('.dropMenu li > a').siblings('ul').find('li').eq(0).find('ul').show();
    });
    //注意：下面的代码是放在iframe引用的子页面中调用
    $(window.parent.document).find("#mainframe").load(function () {
        var main = $(window.parent.document).find("#mainframe");
        var thisheight = $(document).height() + 30;
        main.height(thisheight);
    });
	// 仪表盘 ---- 开始
    // 仪表盘 ----结束
    // 时间插件---开始
    $("#datetimeStart").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        startDate:new Date()
    }).on("click",function(){
        $("#datetimeStart").datetimepicker("setEndDate",$("#datetimeEnd").val());
    });
    $("#datetimeEnd").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        startDate:new Date()
    }).on("click",function(){
        $("#datetimeEnd").datetimepicker("setStartDate",$("#datetimeStart").val());
    });
    // 时间插件 ----结束
    // 访问统计---开始
    var myVisitStatistic = echarts.init(document.getElementById('visitStatistic'));
    // 指定图表的配置项和数据
    var VisitStatistic = {
        tooltip : {
            trigger: 'axis',
            axisPointer: {
                type: 'cross'
            }
        },
        legend: {
            show:true,
            top:10,
            right:10,
            data:[{
                name: 'HBACE',
                icon: 'pin'
            },{
                name: 'HIVE',
                icon: 'pin'
            },{
                name: 'HFDS',
                icon: 'pin'
            }]
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['7月1日','7月2日','7月3日','7月4日','7月5日','7月6日','7月7日']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'HBACE',
                type:'line',
                stack: '总量',
                smooth:true,
                itemStyle : {
                    normal : {
                        color:'#697afa',
                        lineStyle:{
                            color:'#697afa'
                        }
                    }
                },
                areaStyle: {
                    normal: {
                        color:{
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0.5, color: '#697afa' // 0% 处的颜色
                            }, {
                                offset: 1, color: '#697afa' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        }
                    }
                },
                data:[120, 132, 101, 134, 90, 230, 210]
            },{
                name:'HIVE',
                type:'line',
                stack: '总量',
                smooth:true,
                itemStyle : {
                    normal : {
                        color:'#f3bd47',
                        lineStyle:{
                            color:'#f3bd47'
                        }
                    }
                },
                areaStyle: {
                    normal: {
                        color:{
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0.5, color: '#f3bd47' // 0% 处的颜色
                            }, {
                                offset: 1, color: '#f3bd47' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        }
                    }
                },
                data:[220, 182, 191, 234, 290, 330, 310]
            },{
                name:'HFDS',
                type:'line',
                stack: '总量',
                smooth:true,
                itemStyle : {
                    normal : {
                        color:'#06c6a0',
                        lineStyle:{
                            color:'#06c6a0'
                        }
                    }
                },
                areaStyle: {
                    normal: {
                        color:{
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0.5, color: '#06c6a0' // 0% 处的颜色
                            }, {
                                offset: 1, color: '#06c6a0' // 100% 处的颜色
                            }],
                            globalCoord: false // 缺省为 false
                        }
                    }
                },
                data:[220, 182, 191, 234, 290, 330, 310]
            }
        ]
	};
    // 使用刚指定的配置项和数据显示图表。
    myVisitStatistic.setOption(VisitStatistic);

    // 组件风险---开始
    var myriskStatistic = echarts.init(document.getElementById('riskStatistic'));
    // 指定图表的配置项和数据
    var RiskStatistic = {
	    tooltip : {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'cross'
	        }
	    },
	    legend: {
	    	show:true,
	       	top:10,
		    right:10,
		    data:[{
		        name: 'HBACE',
		        icon: 'pin'
		    },{
		       	name: 'HIVE',
		        icon: 'pin'
		    },{
		       	name: 'HFDS',
		        icon: 'pin'
		    }]
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : ['7月1日','7月2日','7月3日','7月4日','7月5日','7月6日','7月7日']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'HBACE',
	            type:'line',
	            stack: '总量',
	            smooth:true,
	            itemStyle : {  
	                    normal : { 
	                    	color:'#697afa',
	                    	lineStyle:{  
	                    		color:'#697afa'  
	                	    }  
	                    }  
	                },
		            areaStyle: {
		            	normal: {
		            		color:{
		            			type: 'linear',
							    x: 0,
							    y: 0,
							    x2: 0,
							    y2: 1,
							    colorStops: [{
							        offset: 0.5, color: '#697afa' // 0% 处的颜色
							    }, {
							        offset: 1, color: '#697afa' // 100% 处的颜色
							    }],
							    globalCoord: false // 缺省为 false
		            		}
		            	}
		        	},
	            data:[120, 132, 101, 134, 90, 230, 210]
	        },{
	            name:'HIVE',
	            type:'line',
	            stack: '总量',
	            smooth:true,
	            itemStyle : {  
	                    normal : { 
	                    	color:'#f3bd47',
	                    	lineStyle:{  
	                    		color:'#f3bd47'  
	                	    }  
	                    }  
	                },
		            areaStyle: {
		            	normal: {
		            		color:{
		            			type: 'linear',
							    x: 0,
							    y: 0,
							    x2: 0,
							    y2: 1,
							    colorStops: [{
							        offset: 0.5, color: '#f3bd47' // 0% 处的颜色
							    }, {
							        offset: 1, color: '#f3bd47' // 100% 处的颜色
							    }],
							    globalCoord: false // 缺省为 false
		            		}
		            	}
		            },
	            data:[220, 182, 191, 234, 290, 330, 310]
	        },{
	            name:'HFDS',
	            type:'line',
	            stack: '总量',
	            smooth:true,
	            itemStyle : {  
	                    normal : { 
	                    	color:'#06c6a0',
	                    	lineStyle:{  
	                    		color:'#06c6a0'  
	                	    }  
	                    }  
	                },
		            areaStyle: {
		            	normal: {
		            		color:{
		            			type: 'linear',
							    x: 0,
							    y: 0,
							    x2: 0,
							    y2: 1,
							    colorStops: [{
							        offset: 0.5, color: '#06c6a0' // 0% 处的颜色
							    }, {
							        offset: 1, color: '#06c6a0' // 100% 处的颜色
							    }],
							    globalCoord: false // 缺省为 false
		            		}
		            	}
		            },
	            data:[220, 182, 191, 234, 290, 330, 310]
	        }
	    ]
	};
    // 使用刚指定的配置项和数据显示图表。
    myriskStatistic.setOption(RiskStatistic);

    // 访问类型---开始
    var myvisitType = echarts.init(document.getElementById('visitType'));
    // 指定图表的配置项和数据
    var VisitType = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    /*legend: {
	        orient: 'vertical',
	        top:20,
		    right:'15',
	        data: ['文件列表','创建文件夹','其他']
	    },*/
	    color:['#697afa', '#06c6a0','#f3bd47'],
	    series : [
	        {
	            name: '访问类型',
	            type: 'pie',
                radius: ['50%', '70%'],
	            center: ['50%', '50%'],
	            data:[{
	            	value:300,name:'文件列表',
	            },{
	            	value:100, name:'创建文件夹'
	            },{
	            	value:150, name:'其他'}
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
    // 使用刚指定的配置项和数据显示图表。
    myvisitType.setOption(VisitType);
    /*窗口自适应，关键代码*/
    $(window).resize(function(){
        myVisitStatistic.resize();
        myriskStatistic.resize();
        myvisitType.resize();
    });
	// 添加集群下拉菜单更新
	var firstColumId="";
    $('.js-firstColum li > a').click(function() {
        firstColumId = $(this).data('id');
        $('.js-firstColum > button').html($(this).text()+'<i class="glyphicon glyphicon-menu-down"></i>');
        $('.js-firstColum > button').data('id',firstColumId);
    });
    // 点击切换 --统计访问右侧菜单
    $(document).on('click','.js-strightNav a',function(){
    	$(this).addClass('active').parents('li').siblings('li').find('a').removeClass('active');
    });
    // 统计访问右侧查询交互
    $(document).on('click','.js-sub',function(){
    	// 获取选中条件
    	var statType = $(this).parents('li').siblings('li').find('a.active').data('type');
    	// 开始时间
    	var startTime = $('#datetimeStart').val();
    	// 结束时间
    	var endTime = $('#datetimeEnd').val();
    	// console.log(statType,startTime,endTime)
    	var params = {
            	statType: statType,
            	startTime:startTime,
            	endTime:endTime
        };
    	var post = $.post('/', params);
            post.done(function(data) {
            	console.log(data)
            });
    });
});