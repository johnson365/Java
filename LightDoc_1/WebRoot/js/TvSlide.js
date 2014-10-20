var tvSlide = {
	data: '',
	currPage: 0,
	currPoint: 0,
	ivm: null,
	pageCount: 0,
	stepCount: 0,
	$pages: '',
	isAnimating : false,
	endCurrPage : false,
	endNextPage : false,
	animEndEventNames : {
			'WebkitAnimation' : 'webkitAnimationEnd',
			'OAnimation' : 'oAnimationEnd',
			'msAnimation' : 'MSAnimationEnd',
			'animation' : 'animationend'
	},
	animEndEventName : '',
	support :'',
	
	//methods
	init: function(data){
		if(tvSlide.ivm)window.clearInterval(tvSlide.ivm);
		if(tvSlide.currPage)tvSlide.currPage=0;
		if(tvSlide.currPoint)tvSlide.currPoint=0;
		if(tvSlide.isAnimating)tvSlide.isAnimating=false;
		if(tvSlide.endCurrPage)tvSlide.endCurrPage=false;
		if(tvSlide.endNextPage)tvSlide.endNextPage=false;
		
		tvSlide.data = data;
		tvSlide.loadContent();
		tvSlide.animEndEventName = tvSlide.animEndEventNames[ Modernizr.prefixed( 'animation' ) ],
		tvSlide.support = Modernizr.cssanimations;
		tvSlide.$pages = $(".homepage-slider-item");
		tvSlide.$pages.each( function() {
			var $page = $( this );
			$page.data( 'originalClassList', 'homepage-slider-item');
		} );
		tvSlide.pageCount = tvSlide.$pages.length;
		tvSlide.$pages.eq(0).addClass("pt-page-current");
		tvSlide.ivm = window.setInterval(tvSlide.movePoint,3000);
		
	},
	loadContent: function(){
		var strArray = new Array();
		$("#progress").html("");
		$("#firstPage .title").html(tvSlide.data.funcTitle);
		$("#firstPage .desc").html(tvSlide.data.funcDesc);
		$(tvSlide.data.stepSets).each(function (i, ss){
			strArray.push("<div class='homepage-slider-item' id='aktuals"+i+"' style='background: url("+ss.imgSrc+") 0 0 no-repeat;'>");
			if(ss.steps.length > 0){
				tvSlide.stepCount += ss.steps.length;
				strArray.push("<div class='stepDesc'><h3>1/"+ss.steps.length+"</h3>"+ss.steps[0].stepDesc+"</div>");
				strArray.push("<img src='images/point.gif' class='img' id='aktuals"+i+"step' style='left:");
				strArray.push(ss.steps[0].offsetX +";top:"+ss.steps[0].offsetY+";'/>");
			}
			strArray.push("</div>");
		});
		var str = strArray.join("");
		$("#firstPage~.homepage-slider-item").remove();
		$("#firstPage").after(str);
		$(".homepage-slider-item").css('background-size', '920px 528px');
	},
	geneDot_list: function(elem){
		var strArray = new Array();
		var steps = tvSlide.data.stepSets[elem].steps;
		if(steps.length > 0){
			$(steps).each(function(i, step){
				strArray.push("<span class='stepProgress' id='prg"+i+"'>"+(i+1)+"</span>");
			});
			var str_dot = strArray.join("");
			$("#progress").html(str_dot);
			$("#prg0").addClass("spanSelect");
		}else{
			$("#progress").html("");
		}
		$(".stepProgress").click(function(event){
			event.preventDefault();
			var i = $(this).html();
			var num = parseInt(i) - 1;
			var stepSet = tvSlide.data.stepSets[tvSlide.currPage-1];
			var elem_id = "#aktuals"+(tvSlide.currPage-1)+"step";
			var len = stepSet.steps.length;
			var step = stepSet.steps[num];
			var offsetX = step.offsetX;
			var offsetY = step.offsetY;
			var stepDesc = step.stepDesc;
			$("#prg"+tvSlide.currPoint).removeClass("spanSelect");
			$("#prg"+num).addClass("spanSelect");
			$(elem_id).animate({left:offsetX, top:offsetY}, "slow", 
					function(){
						$("#aktuals"+(tvSlide.currPage-1)+" .stepDesc").html("<h3>"+(num+1)+"/"+len+"</h3>"+stepDesc);
			});
			tvSlide.currPoint = num;
			tvSlide.reset_interval_movePoint();
		});
	},
	movePoint: function(){
		if(tvSlide.currPage == 0){
			tvSlide.scrollRight();
		}else{
			var stepSet = tvSlide.data.stepSets[tvSlide.currPage-1];
			var len = stepSet.steps.length;
			if(tvSlide.currPoint < len-1)
			{
				var elem_id = "#aktuals"+(tvSlide.currPage-1)+"step";
				var elem = tvSlide.currPoint + 1;
				var len = stepSet.steps.length;
				var step = stepSet.steps[elem];
				var offsetX = step.offsetX;
				var offsetY = step.offsetY;
				var stepDesc = step.stepDesc;
				$("#prg"+tvSlide.currPoint).removeClass("spanSelect");
				$("#prg"+elem).addClass("spanSelect");
				$(elem_id).animate({left:offsetX, top:offsetY}, "slow", function(){$("#aktuals"+(tvSlide.currPage-1)+" .stepDesc").html("<h3>"+(elem+1)+"/"+len+"</h3>"+stepDesc);});
				tvSlide.currPoint = elem;
			}else{
				tvSlide.scrollRight();
			}
		}
	},
	resetPoint: function(elem){
		var stepSet = tvSlide.data.stepSets[elem];
		var len = stepSet.steps.length;
		if(len > 0){
			var step = stepSet.steps[0];
			var offsetX = step.offsetX;
			var offsetY = step.offsetY;
			var stepDesc = step.stepDesc;
			var elem_id = "#aktuals"+elem+"step";
			$("#aktuals"+elem+" .stepDesc").html("<h3>1/"+len+"</h3>"+stepDesc);
			$(elem_id).css({'top':offsetY,'left':offsetX});
		}
		tvSlide.reset_interval_movePoint();
	},
	changeMsg: function(id, stepDesc){
		var elem_id = id + ".stepDesc";
		var elem = $(elem_id);
		elem.html(stepDesc);
	},
	scrollRight: function(){
		if (tvSlide.currPage == tvSlide.pageCount-1)elem = 1;
		else elem = tvSlide.currPage + 1;
		tvSlide.changePic(elem-1);
		tvSlide.nextPage(elem);
		tvSlide.reset_interval_movePoint();
	},
	scrollLefty: function(){
		if (tvSlide.currPage == 1) {elem = tvSlide.pageCount-1;}
		else {elem = tvSlide.currPage - 1;}
		tvSlide.changePic(elem-1);
		tvSlide.nextPage(elem);
		tvSlide.reset_interval_movePoint();
	},
	nextPage: function(elem){
		if(tvSlide.isAnimating )return false;
		tvSlide.isAnimating = true;
		var $currPage = tvSlide.$pages.eq( tvSlide.currPage);
		var $nextPage = tvSlide.$pages.eq( elem).addClass( 'pt-page-current' ),inClass='',outClass='';
		tvSlide.currPage = elem;
		var animation = Math.ceil(Math.random()*67);
		switch( animation ) {
			case 1:
				outClass = 'pt-page-moveToLeft';
				inClass = 'pt-page-moveFromRight';
				break;
			case 2:
				outClass = 'pt-page-moveToRight';
				inClass = 'pt-page-moveFromLeft';
				break;
			case 3:
				outClass = 'pt-page-moveToTop';
				inClass = 'pt-page-moveFromBottom';
				break;
			case 4:
				outClass = 'pt-page-moveToBottom';
				inClass = 'pt-page-moveFromTop';
				break;
			case 5:
				outClass = 'pt-page-fade';
				inClass = 'pt-page-moveFromRight pt-page-ontop';
				break;
			case 6:
				outClass = 'pt-page-fade';
				inClass = 'pt-page-moveFromLeft pt-page-ontop';
				break;
			case 7:
				outClass = 'pt-page-fade';
				inClass = 'pt-page-moveFromBottom pt-page-ontop';
				break;
			case 8:
				outClass = 'pt-page-fade';
				inClass = 'pt-page-moveFromTop pt-page-ontop';
				break;
			case 9:
				outClass = 'pt-page-moveToLeftFade';
				inClass = 'pt-page-moveFromRightFade';
				break;
			case 10:
				outClass = 'pt-page-moveToRightFade';
				inClass = 'pt-page-moveFromLeftFade';
				break;
			case 11:
				outClass = 'pt-page-moveToTopFade';
				inClass = 'pt-page-moveFromBottomFade';
				break;
			case 12:
				outClass = 'pt-page-moveToBottomFade';
				inClass = 'pt-page-moveFromTopFade';
				break;
			case 13:
				outClass = 'pt-page-moveToLeftEasing pt-page-ontop';
				inClass = 'pt-page-moveFromRight';
				break;
			case 14:
				outClass = 'pt-page-moveToRightEasing pt-page-ontop';
				inClass = 'pt-page-moveFromLeft';
				break;
			case 15:
				outClass = 'pt-page-moveToTopEasing pt-page-ontop';
				inClass = 'pt-page-moveFromBottom';
				break;
			case 16:
				outClass = 'pt-page-moveToBottomEasing pt-page-ontop';
				inClass = 'pt-page-moveFromTop';
				break;
			case 17:
				outClass = 'pt-page-scaleDown';
				inClass = 'pt-page-moveFromRight pt-page-ontop';
				break;
			case 18:
				outClass = 'pt-page-scaleDown';
				inClass = 'pt-page-moveFromLeft pt-page-ontop';
				break;
			case 19:
				outClass = 'pt-page-scaleDown';
				inClass = 'pt-page-moveFromBottom pt-page-ontop';
				break;
			case 20:
				outClass = 'pt-page-scaleDown';
				inClass = 'pt-page-moveFromTop pt-page-ontop';
				break;
			case 21:
				outClass = 'pt-page-scaleDown';
				inClass = 'pt-page-scaleUpDown pt-page-delay300';
				break;
			case 22:
				outClass = 'pt-page-scaleDownUp';
				inClass = 'pt-page-scaleUp pt-page-delay300';
				break;
			case 23:
				outClass = 'pt-page-moveToLeft pt-page-ontop';
				inClass = 'pt-page-scaleUp';
				break;
			case 24:
				outClass = 'pt-page-moveToRight pt-page-ontop';
				inClass = 'pt-page-scaleUp';
				break;
			case 25:
				outClass = 'pt-page-moveToTop pt-page-ontop';
				inClass = 'pt-page-scaleUp';
				break;
			case 26:
				outClass = 'pt-page-moveToBottom pt-page-ontop';
				inClass = 'pt-page-scaleUp';
				break;
			case 27:
				outClass = 'pt-page-scaleDownCenter';
				inClass = 'pt-page-scaleUpCenter pt-page-delay400';
				break;
			case 28:
				outClass = 'pt-page-rotateRightSideFirst';
				inClass = 'pt-page-moveFromRight pt-page-delay200 pt-page-ontop';
				break;
			case 29:
				outClass = 'pt-page-rotateLeftSideFirst';
				inClass = 'pt-page-moveFromLeft pt-page-delay200 pt-page-ontop';
				break;
			case 30:
				outClass = 'pt-page-rotateTopSideFirst';
				inClass = 'pt-page-moveFromTop pt-page-delay200 pt-page-ontop';
				break;
			case 31:
				outClass = 'pt-page-rotateBottomSideFirst';
				inClass = 'pt-page-moveFromBottom pt-page-delay200 pt-page-ontop';
				break;
			case 32:
				outClass = 'pt-page-flipOutRight';
				inClass = 'pt-page-flipInLeft pt-page-delay500';
				break;
			case 33:
				outClass = 'pt-page-flipOutLeft';
				inClass = 'pt-page-flipInRight pt-page-delay500';
				break;
			case 34:
				outClass = 'pt-page-flipOutTop';
				inClass = 'pt-page-flipInBottom pt-page-delay500';
				break;
			case 35:
				outClass = 'pt-page-flipOutBottom';
				inClass = 'pt-page-flipInTop pt-page-delay500';
				break;
			case 36:
				outClass = 'pt-page-rotateFall pt-page-ontop';
				inClass = 'pt-page-scaleUp';
				break;
			case 37:
				outClass = 'pt-page-rotateOutNewspaper';
				inClass = 'pt-page-rotateInNewspaper pt-page-delay500';
				break;
			case 38:
				outClass = 'pt-page-rotatePushLeft';
				inClass = 'pt-page-moveFromRight';
				break;
			case 39:
				outClass = 'pt-page-rotatePushRight';
				inClass = 'pt-page-moveFromLeft';
				break;
			case 40:
				outClass = 'pt-page-rotatePushTop';
				inClass = 'pt-page-moveFromBottom';
				break;
			case 41:
				outClass = 'pt-page-rotatePushBottom';
				inClass = 'pt-page-moveFromTop';
				break;
			case 42:
				outClass = 'pt-page-rotatePushLeft';
				inClass = 'pt-page-rotatePullRight pt-page-delay180';
				break;
			case 43:
				outClass = 'pt-page-rotatePushRight';
				inClass = 'pt-page-rotatePullLeft pt-page-delay180';
				break;
			case 44:
				outClass = 'pt-page-rotatePushTop';
				inClass = 'pt-page-rotatePullBottom pt-page-delay180';
				break;
			case 45:
				outClass = 'pt-page-rotatePushBottom';
				inClass = 'pt-page-rotatePullTop pt-page-delay180';
				break;
			case 46:
				outClass = 'pt-page-rotateFoldLeft';
				inClass = 'pt-page-moveFromRightFade';
				break;
			case 47:
				outClass = 'pt-page-rotateFoldRight';
				inClass = 'pt-page-moveFromLeftFade';
				break;
			case 48:
				outClass = 'pt-page-rotateFoldTop';
				inClass = 'pt-page-moveFromBottomFade';
				break;
			case 49:
				outClass = 'pt-page-rotateFoldBottom';
				inClass = 'pt-page-moveFromTopFade';
				break;
			case 50:
				outClass = 'pt-page-moveToRightFade';
				inClass = 'pt-page-rotateUnfoldLeft';
				break;
			case 51:
				outClass = 'pt-page-moveToLeftFade';
				inClass = 'pt-page-rotateUnfoldRight';
				break;
			case 52:
				outClass = 'pt-page-moveToBottomFade';
				inClass = 'pt-page-rotateUnfoldTop';
				break;
			case 53:
				outClass = 'pt-page-moveToTopFade';
				inClass = 'pt-page-rotateUnfoldBottom';
				break;
			case 54:
				outClass = 'pt-page-rotateRoomLeftOut pt-page-ontop';
				inClass = 'pt-page-rotateRoomLeftIn';
				break;
			case 55:
				outClass = 'pt-page-rotateRoomRightOut pt-page-ontop';
				inClass = 'pt-page-rotateRoomRightIn';
				break;
			case 56:
				outClass = 'pt-page-rotateRoomTopOut pt-page-ontop';
				inClass = 'pt-page-rotateRoomTopIn';
				break;
			case 57:
				outClass = 'pt-page-rotateRoomBottomOut pt-page-ontop';
				inClass = 'pt-page-rotateRoomBottomIn';
				break;
			case 58:
				outClass = 'pt-page-rotateCubeLeftOut pt-page-ontop';
				inClass = 'pt-page-rotateCubeLeftIn';
				break;
			case 59:
				outClass = 'pt-page-rotateCubeRightOut pt-page-ontop';
				inClass = 'pt-page-rotateCubeRightIn';
				break;
			case 60:
				outClass = 'pt-page-rotateCubeTopOut pt-page-ontop';
				inClass = 'pt-page-rotateCubeTopIn';
				break;
			case 61:
				outClass = 'pt-page-rotateCubeBottomOut pt-page-ontop';
				inClass = 'pt-page-rotateCubeBottomIn';
				break;
			case 62:
				outClass = 'pt-page-rotateCarouselLeftOut pt-page-ontop';
				inClass = 'pt-page-rotateCarouselLeftIn';
				break;
			case 63:
				outClass = 'pt-page-rotateCarouselRightOut pt-page-ontop';
				inClass = 'pt-page-rotateCarouselRightIn';
				break;
			case 64:
				outClass = 'pt-page-rotateCarouselTopOut pt-page-ontop';
				inClass = 'pt-page-rotateCarouselTopIn';
				break;
			case 65:
				outClass = 'pt-page-rotateCarouselBottomOut pt-page-ontop';
				inClass = 'pt-page-rotateCarouselBottomIn';
				break;
			case 66:
				outClass = 'pt-page-rotateSidesOut';
				inClass = 'pt-page-rotateSidesIn pt-page-delay200';
				break;
			case 67:
				outClass = 'pt-page-rotateSlideOut';
				inClass = 'pt-page-rotateSlideIn';
				break;
		}
		
		$currPage.addClass( outClass ).on(tvSlide.animEndEventName, function() {
			$currPage.off( tvSlide.animEndEventName );
			tvSlide.endCurrPage = true;
			if( tvSlide.endNextPage ) {
				tvSlide.onEndAnimation( $currPage, $nextPage );
			}
		} );

		$nextPage.addClass( inClass ).on(tvSlide.animEndEventName, function() {
			$nextPage.off(tvSlide.animEndEventName );
			tvSlide.endNextPage = true;
			if( tvSlide.endCurrPage ) {
				tvSlide.onEndAnimation( $currPage, $nextPage );
			}
		} );

		if( !this.support ) {
			tvSlide.onEndAnimation( $currPage, $nextPage );
		};
	},
	onEndAnimation:function( $outpage, $inpage ) {
		tvSlide.endCurrPage = false;
		tvSlide.endNextPage = false;
		tvSlide.resetPage( $outpage, $inpage );
		tvSlide.isAnimating = false;
	},
	resetPage:function( $outpage, $inpage ) {
		$outpage.attr( 'class', 'homepage-slider-item');
		$inpage.attr( 'class', 'homepage-slider-item pt-page-current' );
	},
	changePic: function(elem){
		tvSlide.resetPoint(elem);
		tvSlide.geneDot_list(elem);
		tvSlide.currPoint = 0;
	},
	reset_interval_movePoint: function(){
		clearInterval(tvSlide.ivm);
		tvSlide.ivm = setInterval(tvSlide.movePoint,3000);
	},
	modify: function(){
		var $el = $("#aktuals_field");
		if(tvSlide.currPage == 0){
			$.layer({
				type : 1,
				closeBtn : [1, true],
				shade:[0.5 , '#000' , true],
				border : [10 , 0.3 , '#000', true],
				time : 0,
				fix:false,
				title :false,
				offset:['40%','10%'],
				area : ['920px','600px'],
				page:{dom:'#ss_md_div'},
				success : function(){ //层加载成功后进行的回调
					$("#md_title").val($("#firstPage .title").html());
					$("#md_desc").val($("#firstPage .desc").html());
					layer.shift('top',1000); //浏览器上方弹出
				}
			});
		}else{
			var curr = tvSlide.currPage - 1;
			var stepSet = tvSlide.data.stepSets[curr];
			var step = stepSet.steps[tvSlide.currPoint];
			var bg_img = stepSet.imgSrc;
			var offsetX = step.offsetX;
			var offsetY = step.offsetY;
			var stepDesc = step.stepDesc;
			$el.data("num", tvSlide.currPoint);
			$el.data("currPage", tvSlide.currPage);
			$.layer({
				type : 1,
				closeBtn : [1, true],
				shade:[0.5 , '#000' , true],
				border : [10 , 0.3 , '#000', true],
				time : 0,
				fix:false,
				title :false,
				offset:['40%','10%'],
				area : ['920px','600px'],
				page:{dom:'#step_md_div'},
				success : function(){ //层加载成功后进行的回调
					var url = 'url('+bg_img+')';
		    		$("#md_add").css({'background-image':url, 'background-size':'919px 528px'});
		    		$(".md_show").removeClass("md_show");
		    		$("#md_input>.button, #up_div").hide();
		    		$("#btn_mod, #btn_del").show();
		    		$("#md_img").css({'background-image':url, 'background-size':'919px 528px'}).addClass("md_show");
		    		$(".selected").removeClass("selected");
		    		$(".navTab li:first-child a").addClass("selected");
		    		$("#modify_point").css({'top':offsetY,'left':offsetX});
		    		$("#md_stepDesc").text(stepDesc);
		    		layer.shift('top',1000); //浏览器上方弹出
				}
			});
		}
	},
	
};