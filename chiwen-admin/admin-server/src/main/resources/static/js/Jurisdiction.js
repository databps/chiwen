  var setting = {  
            check: {  
                enable: true,
                chkStyle: "radio",
                radioType: 'all'
            },  
            view: {  
                dblClickExpand: false  
            },  
            data: {  
                simpleData: {  
                    enable: true  
                }  
            },  
            callback: {  
                beforeClick: beforeClick,  
                onCheck: onCheck  
            }  
        };  
        function beforeClick(treeId, treeNode) {  
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
            zTree.checkNode(treeNode, !treeNode.checked, null, true);  
            return false;  
        }  
        var zNodes ; 
        function onCheck(e, treeId, treeNode) {  
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            nodes = zTree.getCheckedNodes(true); 
            var v;
            v = '';
            for (var i=0, l=nodes.length; i<l; i++) { 
                v += '<span id="'+nodes[i].id+'">'+nodes[i].name+'<i class="closeI js-close">x<i></span>' 
            }  
            if (v.length > 0 ) v = v.substring(0, v.length-1);  
            var cityObj = $("#citySel"); 
            var aped=cityObj.html();
            cityObj.html(aped + v)
        }  
        var code;  
        function showCode(str) {  
            if (!code) code = $("#code");  
            code.empty();  
            code.append("<li>"+str+"</li>");  
        }  
        function fuzhi(data){ 
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);  
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);  
        }  
        $(document).ready(function(){  
            zNodes =[
                { id:1, pId:0, name:"随意勾选 1", open:true},
                { id:11, pId:1, name:"随意勾选 1-1", open:true},
                { id:111, pId:11, name:"随意勾选 1-1-1"},
                { id:112, pId:11, name:"随意勾选 1-1-2"},
                { id:12, pId:1, name:"随意勾选 1-2",open:true},
                { id:121, pId:12, name:"随意勾选 1-2-1"},
                { id:122, pId:12, name:"随意勾选 1-2-2"},
                { id:2, pId:0, name:"随意勾选 2", open:true},
                { id:21, pId:2, name:"随意勾选 2-1"},
                { id:22, pId:2, name:"随意勾选 2-2", open:true},
                { id:221, pId:22, name:"随意勾选 2-2-1"},
                { id:222, pId:22, name:"随意勾选 2-2-2"},
                { id:23, pId:2, name:"随意勾选 2-3"}
            ];    
        // $.get("http://localhost:8090/Test/servlet/tree",function(data){  
            //$('#result').text(zNodes);//直接展示JSON数据  
                fuzhi(zNodes);  
            }); 
        $(document).on('click','#citySel',function(e){     
            $('.js-ztreesBox').slideDown()
            $(document).one('click',function(){
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.expandAll(true);//折叠全部节点,参数为true时表示展开全部节点  
                zTree.refresh();//刷新zTree，实现不选中任何节点 
                $('.js-ztreesBox').hide()
            })
            e.stopPropagation();
        }) 
        $(document).on('click','.js-close',function(e){
            $(this).parent().remove();
            e.stopPropagation();
        })
        // }); 