//用户在载入页面的时候生成一个cookie
var cookieNumber=setTokenVal();
setCookie("token",cookieNumber);

//页面载入时就添加数据
$.ajax({
    type:'GET',
    dataType:'json',
    url:'giftpage',  //改~~~~
    data:{
    	"gameId":1
    },
    beforesend:function(){
        console.log("加载中。。。");
    },
    success:function(data){
    	console.log(data);
        var commonGifHtml='',
            vipGifHtml='';
        for(var i=0;i<data.length;i++){
            if(data[i].giftType.id==1){
                commonGifHtml+='<li>' +
                    '<div class="gif_wrap">' +
                    '<i></i>' +
                    '<div class="gif_info">' +
                    '<div class="gif_info_title">'+data[i].giftName+'</div>' +
                    '<div class="gif_info_detail txt_ellipsis">'+data[i].giftDetails+'</div>' +
                    '</div>' +
                    '<div class="gif_num">' +
                    '<div>数量:<span>'+data[i].giftNum+'</span>/<span>'+data[i].giftTotal+'</span></div>' +
                    '</div>' +
                    '</div>' +
                    '</li>';
            }else if(data[i].giftType.id==2){
                vipGifHtml+='<li>' +
                    '<i></i>' +
                    '<div class="gif_info">' +
                    '<div class="gif_info_title">'+data[i].giftName+'</div>' +
                    '<div class="gif_info_detail txt_ellipsis">'+data[i].giftDetails+'</div>' +
                    '</div>' +
                    '<div class="gif_num">' +
                    '<div>数量:<span>'+data[i].giftNum+'</span>/<span>'+data[i].giftTotal+'</span></div>' +
                    '</div>' +
                    '</li>';
            }
        }
        $('.common_gif ul').append(commonGifHtml);
        $('.vip_gif ul').append(vipGifHtml);
        //console.log(data);
    },
    error:function(){
        alert("页面加载失败请重试");
    }
});


//文本框获得焦点时清空提示消息
$('#phoneNumber').focus(function(){
    $('.errormsg').html('');
});
$('#verifyCode').focus(function(){
    $('.errormsg').html('');
});



//发送验证码
//var _phoneNumber;//记录用户在手机号码输入框输入的号码
$('#sendValidNum').on('click',function(e){
    var phoneNumber=$('#phoneNumber').val();
    //_phoneNumber=phoneNumber;
    var _this=$(this);
    console.log('发送了一次');
    if(phoneNumber){
        if(!(/^1[34578]\d{9}$/.test(phoneNumber))){
            $('.errormsg').html('手机号码有误，请重填~');
            return false;
        }else{
        	_this.attr('disabled',true);
        	$.ajax({
                type:'GET',
                dataType:'json',
                url:'sendVerifyCode',  //改~~~~
                data:{
                    "phoneNumber":phoneNumber,"cookieNumber":cookieNumber
                },
                success:function(data){
                    _this.attr('disabled',true);
                    var count=60;  //设置一次发送验证码时长
                    var timer=window.setInterval(function(){
                        count--;
                        if(count<=0){
                            window.clearInterval(timer);
                            _this.val('重发验证码');
                            _this.attr('disabled',false);
                        }else{
                            _this.val(count+"秒后重发");
                        }
                    },1000);
                },
                error:function(){
                    $('.errormsg').html('验证码发送失败，请重试~');
                }
            });
        }
    }else{
        $('.errormsg').html('请输入您的手机号码~');
        _this.attr('disabled',false);
    }

});

//检验验证码
$('#sendConfirmNum').on('click',function(e){
    var verifyCode=$('#verifyCode').val();
    var phoneNumber=$('#phoneNumber').val();
    console.log('发送了验证码');
    if(verifyCode && phoneNumber){
        if(!(/^\d{4}$/.test(verifyCode))){
            $('.errormsg').html('验证码格式有误，请输入四位数字~');
            return false;
        }
        console.log("后台请求验证码");
        $.ajax({
            type:'GET',
            dataType:'json',
            url:'check',  //改~~~~
            data:{
                "phoneNumber":phoneNumber,"verifyCode":verifyCode,"cookieNumber":cookieNumber
            },
            beforesend:function(){
                $('.errormsg').html('确认中...');
            },
            success:function(data){
            	console.log(data);
            	if(data[0].json.success){
                //礼包信息部分
                var pageList=data[0].pageList;
                var fistPages=data[0].fistPages;
                console.log(fistPages);
                var commonGifHtml='';
                var vipGifHtml='';
                var commonGifLength=0;
                for(var i=0;i<pageList.length;i++){
                    if(pageList[i].giftType.id==1){
                        commonGifLength++;
                       
                        commonGifHtml+='<li>' +
                            '<div class="gif_wrap">' +
                            '<i></i>' +
                            '<div class="gif_info">' +
                            '<div class="gif_info_title">'+pageList[i].giftName+'</div>' +
                            '<div class="gif_info_detail txt_ellipsis">'+pageList[i].giftDetails+'</div>' +
                            '</div>' +
                            '<div class="gif_num">' +
                            '<div>数量:<span>'+pageList[i].giftNum+'</span>/<span>'+pageList[i].giftTotal+'</span></div>' +
                            '</div>' +
                            '</div>' +
                            '</li>';
                    }else if(pageList[i].giftType.id==2){
                        vipGifHtml+='<li>' +
                            '<i></i>' +
                            '<div class="gif_info">' +
                            '<div class="gif_info_title">'+pageList[i].giftName+'</div>' +
                            '<div class="gif_info_detail txt_ellipsis">'+pageList[i].giftDetails+'</div>' +
                            '</div>' +
                            '<div class="gif_num">' +
                            '<div>数量:<span>'+pageList[i].giftNum+'</span>/<span>'+pageList[i].giftTotal+'</span></div>' +
                            '</div>' +
                            '</li>';
                    }
                }
                $('.common_gif ul').html(commonGifHtml);
                //$('.vip_gif ul').html(vipGifHtml);

                //激活码部分
                var activeCodeList=data[0].activeCodeList;
                var actCodeHtml='';
                for(var j=0;j<commonGifLength;j++){
                	
                    if(fistPages[j].giftNum>0){
                        actCodeHtml='<div class="gif_code">' +
                            '<em>礼包激活码</em>' +
                            '<span class="code" class="actCodeVal" id="actCodeVal'+j+'">'+activeCodeList[j].activeCode+'</span>' +
                            '<span class="copycode" data-clipboard-action="copy" id="copycode'+j+'" data-clipboard-target="#actCodeVal'+j+'">复制</span>' +
                            '</div>';
                        $('.common_gif ul li:eq('+j+')').append(actCodeHtml);
                        $('.common_gif li:eq('+j+')').css('height','0.7rem');
                    }
                }

                //复制功能只能click
                $('.copycode').on('click',function(){
                    console.log("copy!");
                });

                var clipboard = new Clipboard('.copycode');
                clipboard.on('success', function(e) {
                    // window.getSelection().removeAllRanges();
                    //alert('复制成功');
                    $('.ttoastCopy').stop(true,false).fadeIn(500).fadeOut(1000);
                    console.log(e.text);
                    e.clearSelection();

                });
                clipboard.on('error', function(e) {
                    alert("失败");
                });


                //遮罩和弹窗消失
                $('.keytoget').hide();
                $('.mask').hide();
                $('.validtelwin').hide();
            }else{
                 	console.log();
                     $('.errormsg').html(data[0].json.msg);
            }
            },
            error:function(){
                $('.errormsg').html('短信验证码错误，请重试');
                
            }
           
        });
    }
    else if(!phoneNumber){
        $('.errormsg').html('请输入您的手机号码~');
    }else if(!verifyCode){
        $('.errormsg').html('请输入您收到的短信验证码~');
    }
});


//设置cookie
function setCookie(name, value, Days){
    if(Days == null || Days == ''){
        Days = 365;
    }
    var exp  = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + "; path=/;expires=" + exp.toGMTString();
}

//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
}

//设置cookie的值
function setTokenVal(){
    var randomStr='';
    for(var i=0;i<10;i++){
        var n=Math.floor(Math.random()*10);
        randomStr+=n;
    }
    var nowSecond=(new Date().getTime()).toString().substr(-5);
    var cookieVal=randomStr+nowSecond;
    return cookieVal;
}

//vip礼包弹出层
$('.vip_gif ul').on('touchstart',function(e){
    $('.copyfail').show();
});

$('.closeValidwin').on('touchstart',function(e){
    $('.copyfail').hide();
});