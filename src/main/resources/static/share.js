var _ctx = $("meta[name='_ctx']").attr("content");
_ctx = _ctx.substr(0, _ctx.length - 1);
/*
 ** 时间戳转换成指定格式日期
 ** eg. 
 ** dateFormat(11111111111111, 'Y年m月d日 H时i分')
 ** → "2322年02月06日 03时45分"
 */
var dateFormat = function(timestamp, formats) {
	// formats格式包括
	// 1. Y-m-d
	// 2. Y-m-d H:i:s
	// 3. Y年m月d日
	// 4. Y年m月d日 H时i分
	formats = formats || 'Y-m-d';

	var zero = function(value) {
		if (value < 10) {
			return '0' + value;
		}
		return value;
	};
	if(!timestamp){
		return "暂无";
	}
	var myDate = timestamp ? new Date(timestamp) : new Date();

	var year = myDate.getFullYear();
	var month = zero(myDate.getMonth() + 1);
	var day = zero(myDate.getDate());

	var hour = zero(myDate.getHours());
	var minite = zero(myDate.getMinutes());
	var second = zero(myDate.getSeconds());

	return formats.replace(/Y|m|d|H|i|s/ig, function(matches) {
		return ({
			Y : year,
			m : month,
			d : day,
			H : hour,
			i : minite,
			s : second
		})[matches];
	});
};


//定义到jQuery全局变量下-文件下载
//用法：
//$.download('/FileExport/DownLoadFile', 'post', data.value); // 下载文件
jQuery.download = function (url, method, filedir) {
jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' +  // action请求路径及推送方法
            '<input type="text" name="filePath" value="' + filedir + '"/>' + // 文件路径
        '</form>')
.appendTo('body').submit().remove();
};

//yyyy-MM-dd hh:mm:ss转为date
function convertDateFromString(dateString) {
	if (dateString) {
		var arr1 = dateString.split(" ");
		var sdate = arr1[0].split('-');
		var time=arr1.length==2?arr1[1]:'00:00:00';
		var sTime=time.split(':');
		var date = new Date(sdate[0], sdate[1] - 1, sdate[2],sTime[0],sTime[1],sTime[2]);
		return date;
	}
}


/**打开地址选择框 用法如下
 * addressSelectWindow("floor",function(res){console.log(res);})
 * @param except 去除那个级别的级联子列表,有region/zjlx/floor/door/house
 * @param callback 回调函数,其中res是选中的节点数据,如{id: "520102", label: "南明区", type: "region"}
 * @returns
 */
function addressSelectWindow(except,callback){
	layui.use('layer', function(){
    	  var layer = layui.layer;
    	  var index = layer.open({
	    	  type: 2,
	    	  title: "选择地址",
	    	  btn: ['确定','关闭'],
	    	  content: _ctx+"/sys-house/addressSelect?except="+except,
	    	  area: ['300px', '80%'],
	    	  maxmin: true,
	    	  btnAlign: 'c',
	    	  offset: ['100px', '200px'],
	    	  yes: function(index){
                    //当点击‘确定’按钮的时候，获取弹出层返回的值
                    var res = window["layui-layer-iframe" + index].callbackdata();
                    if(res==null){
                    	layer.msg("请选择适合的节点");
                    }else{
                    	layer.close(index);
                    }
                    callback(res);
                }
	    	});
    	});
}





