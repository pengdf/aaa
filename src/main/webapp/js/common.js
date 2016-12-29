//根据根节点定字体大小
var maxw=$('body').width();
$('body').css('max-width',maxw);

window.onresize=function(){
    var scale=document.body.clientWidth/360;
    var html=document.getElementsByTagName('html')[0];
    html.style['font-size']=100*scale+'px';
}
var scale=document.body.clientWidth/360;
var html=document.getElementsByTagName('html')[0];
html.style['font-size']=100*scale+'px';

//toast 底部
$('.bottom_getgift').on('touchstart',function(e){
    //遮罩
    //$('.mask').show();
    //$('body').css('overflow','hidden');
});


//正文部分占位盒子高度
var bodyH=$('body').height();
//var topad=$('.ios_topad').height();
var back=$('.ios_back').height();
var links=$('.ios_links').height();
var kefu=$('.ios_kefu').height();
var iosWebContHeight=back;
var reduceH=parseInt(bodyH-iosWebContHeight);//23
$('.ttarticle').css('height',reduceH);

//首页黑盒
var banner=$('.ios_ttbanner').height();
var inwrap=$('.ios_ttinwrap').height();
var iosWebindex=banner+inwrap+links+kefu;
var indexreduceH=parseInt(bodyH-iosWebindex-6);
$('.placeholder').css('height',indexreduceH);

