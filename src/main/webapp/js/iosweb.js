//tab栏切换
$('.ios_allinfo').on('touchstart',function(){
    $(this).addClass('on').siblings().removeClass('on');
});

$('.ios_newslist').on('touchstart',function(){
    $(this).addClass('on').siblings().removeClass('on');
});

$('.ios_gonglue').on('touchstart',function(){
    $(this).addClass('on').siblings().removeClass('on');
});

$('.ios_libao').on('touchstart',function(e){
    $(this).addClass('on').siblings().removeClass('on');
});

$('.ios_activity').on('touchstart',function(e){
    //$(this).removeClass('on');
    $('.ttoast').stop(true,false).fadeIn(500).fadeOut(2500);
});

//资讯ajax请求
//function ajax(parms) {
//    function getJson(callback){
//        $.ajax({
//            url: parms.url,
//            success:function(data){
//                callback(data);
//            }
//        });
//    }
//
//    var arrayData = [];
//    getJson(function (data) {
//        arrayData = data;
//        var array = dataHandle(arrayData);
//        setTimeout(function() {
//            parms.success(array);
//        }, 200);
//    });
//
//    function dataHandle(arrayData) {
//        var dataNum = +$(parms.id).attr("dataNum");
//        var totlaLength = arrayData.length;
//        var maxPage = Math.ceil(totlaLength / 6);
//        //if(dataNum>maxPage-1){
//        //    $('.jroll-infinite-tip').html("...");
//        //    //return;
//        //}
//        var startIndex = dataNum * 6;
//        var array = arrayData.slice(startIndex, 6 * (dataNum + 1));
//        //console.log(array.length);
//        if(array.length<=0){
//            $(''+parms.id+' .jroll-infinite-tip').html("—— 到底了——");
//            return;
//        }
//        dataNum = dataNum + 1;
//        $(parms.id).attr("dataNum", dataNum);
//        return array;
//    }
//}

$('#infoall').on('touchstart',function(e){
    $('#wrapper_gl').hide();
    $('#wrapper_xw').hide();
    $('#wrapper_lb').hide();
    $('#wrapper').show();
})
//var jroll = new JRoll("#wrapper", {scrollBarY:true});
//jroll.infinite({
//    getData : function(page, callback){
//        ajax({
//            id:"#wrapper",
//            type:"get",
//            url:'json/ttcontent.json',
//            success: function(data) {
//                callback(data);
//                console.log(data);
//            }
//        });
//    },
//    compile: function(text) {
//        return _.template(text);
//    },
//    template: "<li class='txt_ellipsis'><span class='<%=className%>'><%=tag%><i>|</i></span><a href='<%=link%>'><%=title%></a></li>",
//    loadingTip:"<div class=\"jroll-infinite-tip\">正在加载...</div>"
//});

//攻略
var ghflag=true;
$('#gonglue').on('touchstart',function(e){
    $('#wrapper').hide();
    $('#wrapper_xw').hide();
    $('#wrapper_lb').hide();
    $('#wrapper_gl').show();
    if(!ghflag) return;

//    var jroll = new JRoll("#wrapper_gl", {scrollBarY:true});
//    jroll.infinite({
//        getData : function(page, callback){
//            ajax({
//                id:"#wrapper_gl",
//                url:'/json/gonglue.json',
//                success: function(data) {
//                    callback(data);
//                }
//            });
//        },
//        compile: function(text) {
//            return _.template(text);
//        },
//        //template: "<li class='txt_ellipsis'><span class='<%=className%>'><%=tag%><i>|</i></span><a href='<%=link%>'><%=title%></a></li>",
//        template: "<li class='txt_ellipsis'><span><%=tag%><i>|</i></span><a href='<%=link%>'><%=title%></a></li>",
//        loadingTip:"<div class=\"jroll-infinite-tip\">正在加载...</div>"
//    });
    ghflag=false;
});

//新闻
//var xwflag=true;
$('#newslist').on('touchstart',function(e){
    $('#wrapper').hide();
    $('#wrapper_gl').hide();
    $('#wrapper_lb').hide();
    $('#wrapper_xw').show();
});

//礼包
$('.ios_libao').on('touchstart',function(e){
    $('#wrapper').hide();
    $('#wrapper_gl').hide();
    $('#wrapper_xw').hide();
    $('#wrapper_lb').show();
});

$('.bottom_getgift a').on('touchstart',function(e){
    $('#wrapper').hide();
    $('#wrapper_gl').hide();
    $('#wrapper_xw').hide();
    $('#wrapper_lb').show();
    $('.ios_libao').addClass('on').siblings().removeClass('on');
});

//一键领取
$('.keytoget').on('touchstart',function(e){
    $('.mask').show();
    $('.validtelwin').show();
});

$('#closeValidwin').on('touchstart',function(e){
    $('.mask').hide();
    $('.validtelwin').hide();
    $('#phoneNumber').val('');
    $('#verifyCode').val('');
    $('.errormsg').html('');
});

