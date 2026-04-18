/*
 * Zusammengefasste JQuery Funktionalitäten
 */

$(window).load(function() {
    $.fn.hideFSBusy();
    // //console.log("window load ...");
});

$(document).ready(function() {

    // Wird dynamisch per Wicket aktiviert
    // $.fn.startTimer(1);

    // $.fn.setupWicketAjaxFunc();
    // $.fn.switchFoundHint();
    // $.fn.switchEmptySearch();
    // $.fn.btnAnmeldenFade();
    // $.fn.initTogglePanel();
    // $.fn.initSearchButtonSelector();
    //
    // $.fn.deleteSearchTextfields();
    // //$.fn.initDateSearchRecherche();
    // //$.fn.initDateSearchStatistik();
    //$.fn.initDatePickerHalde();
    //    
    // // Wenn das aktiviert ist geht das autocomplete nicht!
    // //$.fn.iedetection();
    // //$.fn.csvklicked();
    //$.fn.removeSelectFocus();
    // //$.fn.setTableHeight();
    
    // $.fn.initFixedTableHeader();
    // $.fn.initFixedTableHeaderRec();
    // $.fn.initFixedTableArcNew();
    // $.fn.initFixedTableHeaderRights();
    
    // //$.fn.showPwdChange(false);
    // //$.fn.shortenPaging();
    // $.fn.hideViewBusy();
    // $.fn.testlinkklicked();
    // $.fn.colSwitchKlicked();
    // //$.fn.searchKlicked();
    // $.fn.closeHelpHalde();
    // $.fn.tableSelector();
    //$.fn.testlinkklicked();
    //$.fn.onclikcselect();
});

(function($) {

    // $.fn.shortenPaging = function() {
    // $('.shorten > a > span').text('...');
    // };

    $.fn.tableSelector = function() {

        // $("#data td").toggle(function(){
        // $(this).css('background-color','blue')
        // },function(){
        // $(this).css('background-color','ur_default_color')
        // });
    }

    $.fn.showPwdChanged = function() {
        alert('Ihr Passwort wurde geändert!');
        document.location.href = "/archiv";
    };

    $.fn.showUserCreated = function() {
        alert('Der Benutzer wurde angelegt!');
    };

    $.fn.showUserChanged = function() {
        alert('Der Benutzer wurde geändert!');
    };

    $.fn.showPwdReset = function(ben) {
        var answer = confirm('Das Passwort des Benutzers: ' + ben + ' wird zurückgesetzt!');
        if (answer) {
            $(".pwdreset").click();
        } else {
            $(".search").click();
            // zuruecknehmen der selektion
            // $('.pwdressel').attr('checked', false);
        }
    };

    $.fn.closeHelpHalde = function() {
        $("a.closehaldehelp").click(function() {
            $('.tab-panel .view iframe').css('width', '98%');
            $('.callout.border-callout').css('display', 'none');
        });
    };

    $.fn.clickHiddenLink = function() {
        $("a.hiddenlink")[0].click();
    }

    $.fn.showPwdReset2 = function(ben) {
        var answer = confirm('Das Passwort des Benutzers: ' + ben + ' wird zurückgesetzt!');
        if (answer) {
            $("a.pwdreset2")[0].click();
        } else {
            $(".search").click();
            // zuruecknehmen der selektion
            // $('.pwdressel').attr('checked', false);
        }
    };

    $.fn.showUser2Change = function() {
        var answer = confirm('Es befinden sich nicht gespeicherte Benutzer im System. Sollen die Benutzer gespeichert werden?');
        if (answer) {
            // $(".search" ).click();
            $("input.saveuser")[0].click();
        } else {
            $("input.abbortuser")[0].click();
        }
    };

    $.fn.showDeleteUser = function(ben) {
        var answer = confirm('Der Benutzer wird gelöscht: ' + ben + ' !');
        if (answer) {
            $("a.deleteUserR")[0].click();
        } else {
            $(".search").click();
        }
    };

    $.fn.showPwdNotChanged = function(adress) {
        alert('Ihr Passwort wurde nicht geändert!');
        document.location.href = "/archiv";
    };

    $.fn.initFixedTableHeader = function() {
        var paginationActive = (!($('.tab-panel .view .pagination span.goto').text().length == 0));
        if (!paginationActive) {
            $('.tab-panel .view .csv').css('display', 'none'); // .csv Button ausblenden
            $('.tab-panel .view .pdf').css('display', 'none'); // .csv Button ausblenden
            $('.tab-panel .view .pagination').css('display', 'none'); // Paginierung ausblenden
        }
        $('.fixedHeaderTbl').fixedHeaderTable({
            height : '590px',
            sortable : 'true',
            footer : 'true'
        });
    };

    $.fn.initFixedTableHeaderRec = function() {
        $('.fixedHeaderTblRec').fixedHeaderTable({
            height : '300px',
            sortable : 'true',
            footer : 'true'
        });
    };

    $.fn.initFixedTableArcNew = function() {
        $('.fixedHeaderTblArcNew').fixedHeaderTable({
            height : '150px',
            correctlastcolumn : 'false'
        });
    };

    $.fn.initFixedTableHeaderRights = function() {
        $('.fixedHeaderTblRights').fixedHeaderTable({
            height : '567px',
            sortable : 'true'
        });
    };

    $.fn.setTableHeight = function() {
        var height = $(".searchForm1").height(); // optionally, subtract some
                                                    // from the height
        // $("#content").css("height", height + "px");
        if (height > 1) {
            $(".view").css("height", height + 70 + "px");
        }
    };

    $.fn.iedetection = function() {

        /* IE 10 Hack fuer die Select-Boxen */
        if ($.browser.msie && $.browser.version < 10) {
            $("html").addClass("ielt10");
        }
        if (ie_ver() == 10) {
            $("html").addClass("ie10");
        }
        if (ie_ver() == 11) {
            $("html").addClass("ie11");
        }
        // console.log("Browser Version: " + $.browser.version);
        //console.log("Browser Version: " + ie_ver());
    };

    /**
     * Funktioniert nur für den 10er u. 11er!
     */
    function ie_ver() {  
        var iev=0;
        var ieold = (/MSIE (\d+\.\d+);/.test(navigator.userAgent));
        var trident = !!navigator.userAgent.match(/Trident\/7.0/);
        var rv=navigator.userAgent.indexOf("rv:11.0");

        if (ieold) iev=new Number(RegExp.$1);
        if (navigator.appVersion.indexOf("MSIE 10") != -1) iev=10;
        if (trident&&rv!=-1) iev=11;

        return iev;         
    }
    
    $.fn.removeSelectFocus = function() {
        var c = 0;

        $("select").bind('click', function() {
            // event.stopPropagation();
            if (c++ % 2 == 1) {
                // console.log(c);
                $(this).blur();
            }
        });

        $('html').click(function() {
            if ($('select').is(':focus'))
                c = 1;
            else
                c = 0;
        });
    };

    // $.fn.csvklicked = function(){
    // $("a.csv").click(function(){
    // alert('csv cklicked');
    // });
    // };
//    $.fn.testlinkklicked = function() {
//        $("a.testlink2").click(function() {
//            //$.fn.showViewBusy();
//            console.log('a.testlink2 clicked');
//            //$('testcol2').focus();
//            //$('testcol2').click();
//            //$("#lieferantenname18e").trigger('change');
//            var txtVal = $('.testcol2 input').attr('value');
//            $('.testcol2 input').val(txtVal + '-');
//            
//            console.log('Textkontrolle: ' + $('.testcol2 input').attr('value'));
//        });
//    };
//
//    $.fn.onclikcselect = function() {
//        //console.log('$.fn.onclikcselect acitvatet');
//        //$(document).on('click','input[type=text]',function(){ this.select(); });
//        //console.log('Textkontrolle: ' + $('.testcol2 input').val());
//    }
    
    /**
     * Testblase, um Spalten wegklappen zu können.
     */
    $.fn.colSwitchKlicked = function() {
        $("th.switchcol").click(function() {
            var level = $(this).attr('class').split(' ')[1];
            // console.log($(this));
            // console.log('level: ' + level);

            if ($("td.level" + level).hasClass("invisible")) {
                $("td.level" + level).removeClass('invisible');
                $("th.level" + level).removeClass('invisible');
            } else {
                $("td.level" + level).addClass('invisible');
                $("th.level" + level).addClass('invisible');
            }

        });
    };

    /*
     * $.fn.searchKlicked = function(){ console.log("SearchKlicked
     * initialized"); $(".search").click(function(){
     * console.log("SearchKlicked"); $.fn.showViewBusy(); }); };
     */

    $.fn.deleteSearchTextfields = function() {
        $(".deletesearch > a").click(function() {
            // console.log("d-klicked");
            $(".switchpanel input").val('');
        });
    };

    $.fn.initDateSearchRecherche = function() {
        $.datepicker.setDefaults($.datepicker.regional["de"]);
        $("#datepickerVon, #datepickerBis").datepicker({
            changeMonth : true,
            changeYear : true,
            showOn : "button",
            buttonImage : "image/calendar.gif",
            dateFormat : "yymmdd",
            buttonImageOnly : true,
            maxDate: new Date()
        });
    };
    $.fn.initDateSearchStatistik = function() {
        $.datepicker.setDefaults($.datepicker.regional["de"]);
        $("#datepickerVonStat, #datepickerBisStat").datepicker({
            changeMonth : true,
            changeYear : true,
            showOn : "button",
            buttonImage : "image/calendar.gif",
            dateFormat : "yy-mm-dd",
            buttonImageOnly : true,
            maxDate: new Date()
        });
    };

    // https://api.jqueryui.com/datepicker/#utility-formatDate
    $.fn.initDatePickerHalde = function() {
        //console.log("initDatePickerHalde");
        //$.datepicker.setDefaults($.datepicker.regional["de"]);

        $(".datePickerHalde").datepicker({
            changeMonth : true,
            changeYear : true,
            language: "de",
            buttonImage : "image/calendar.gif",
            yearRange: 'c-11:c+1',
            dateFormat: 'dd.mm.yy',
            onClose: function(dateText, inst) {
                $(this).datepicker('option', 'dateFormat', 'dd.mm.yy')
            },
            beforeShow : function(textbox, instance) {
                if(ie_ver()){
                    instance.dpDiv.css({
                        marginTop : textbox.offsetHeight -230 + 'px',
                        marginLeft : textbox.offsetWidth -175 + 'px'
                    });
                }
            },
            maxDate: new Date()
        });
        
//        $(".ie11 .datePickerHalde").datepicker({
//            changeMonth : true,
//            changeYear : true,
//            language: "de",
//            buttonImage : "image/calendar.gif",
//            yearRange: 'c-11:c+1',
//            dateFormat: 'dd.mm.yy',
//            onClose: function(dateText, inst) {
//                $(this).datepicker('option', 'dateFormat', 'dd.mm.yy')
//            },
//            beforeShow : function(textbox, instance) {
//                instance.dpDiv.css({
//                    marginTop : (-textbox.offsetHeight) + 'px',
//                    marginLeft : textbox.offsetWidth + 'px'
//                });
//            }
//        });
        
        // IE-Workaround. Der Datepicker verschwindet hinter dem PDF.
        //$(".ielt10 #ui-datepicker-div").wrap('<div style="position:absolute;left:0px;top:-190px;"></div>');
        //$(".ie10 #ui-datepicker-div").wrap('<div style="position:absolute;left:0px;top:-190px;"></div>');
        //$(".ie11 #ui-datepicker-div").wrap('<div style="position:absolute;left:0px;top:-190px;"></div>');

        /*
        .click(function() {
            $(".ielt10 .wrappview form.bearbeiten").css({"margin-bottom": "160px"});
            $(".ie10 .wrappview form.bearbeiten").css({"margin-bottom": "160px"});
            $(".ie11 .wrappview form.bearbeiten").css({"margin-bottom": "160px"});
        });
        // IE-Workaround. Der Datepicker verschwindet hinter dem PDF.
        $(".calendar").change(function(){
            $(".ielt10 .wrappview form.bearbeiten").css({"margin-bottom": "0px"});
            $(".ie10 .wrappview form.bearbeiten").css({"margin-bottom": "0px"});
            $(".ie11 .wrappview form.bearbeiten").css({"margin-bottom": "0px"});
        });
        */
    };
    
    $.fn.disableFieldsAndButton = function() {
        $(".tab-panel form.search div input:text").attr('disabled', 'disabled');
        // $(".tab-panel form.search fieldset
        // input[type=submit]").attr('disabled', 'disabled');
    }

    $.fn.disableButtonEvent = function() {

        var formID = $('form.searchForm1').attr('id');
        if (formID) {
            formID = '#' + formID;
            var btn1ID = $(formID + ' input[type=submit]').attr('id');
            $('#' + btn1ID).click(function() {
                // console.log("Klick");
                return false;
            });
        }
    }

    /**
     * Blendet den iframe nur ein, wenn die Adressierung nicht null ist.
     */
    $.fn.showIframe = function() {
        var iframeAdr = $("iframe").attr("src");
        // console.log('IFrame Wert-Check: ' + "\"" + iframeAdr + "\"");
        if (iframeAdr) {
            $("iframe").removeClass("invisible");
        } else {
            $("iframe").addClass("invisible");
        }
    }

    $.fn.handleServersideToggle = function() {

        if ($(".tab-panel form.search .headline1 a").hasClass("imgswitch-off")) {
            $(".switchpanel1").hide('400');
        } else {
            $(".switchpanel1").show('400');
        }
    }

    $.fn.initTogglePanel = function() {
        $(".tab-panel form.search fieldset .headline1").click(function() {
            // console.log("klick");
            var headlineElement = $(this);
            $(headlineElement).next(".switchpanel").slideToggle(400, function() {
                if ($(this).is(':visible')) {
                    $(headlineElement).children("a").removeClass("imgswitch-off");
                    $(headlineElement).children("a").addClass("imgswitch-on");
                } else {
                    $(headlineElement).children("a").removeClass("imgswitch-on");
                    $(headlineElement).children("a").addClass("imgswitch-off");
                }
            });
        });
    };

    $.fn.initTableSelector = function() {
        // Zeile angeklickt ...
        $('table tbody tr').click(function(e1) {
            // console.log('tr klicked');
            var link = $(this).children('td').children('a.selhook').attr('href');
            // console.log('href 1: ' + link);
            if (link) {
                document.location.href = link;
            }
        });
    };

    $.fn.initSearchButtonSelector = function() {

        
        var formID = $('form.searchForm1').attr('id');
        // console.log('form id ermittelt1 : ' + formID);

        if (formID) {
            formID = '#' + formID;
            // console.log('form id ermittelt2 : ' + formID);

            var btn1ID = $(formID + ' input[type=submit]').attr('id');
            // console.log('button id ermittelt : ' + btn1ID);

            $(formID + ' input[type=submit]').click(function() {
                switch ($(this).val()) {
                case "SUCHE STARTEN": {
                     console.log("SUCHE STARTEN");
                    //$.fn.showFSBusy();
                    $.fn.showViewBusy();
                    break;
                }
                case "DUMMYBUTTON": {
                    // console.log("DUMMYBUTTON");
                    break;
                }
                }
            });

            // $('#srcbtn').submit(function(e) {

            // alert("Form-Id: " + formID);
            // var form = this; // Debughelfer, Submit-Verzögerung
            // e.preventDefault(); // Debughelfer, Submit-Verzögerung
            // setTimeout(function () {form.submit();}, 5000); //
            // Debughelfer, Submit-Verzögerung

            // $.fn.showFSBusy();
            // $('.searchBusy').removeClass('invisible');
            // });
        }
    };

    /**
     * Anmeldebutton, Smootheffekt.
     */
    $.fn.btnAnmeldenFade = function() {
        $(".img-btn-anmelden-on").hover(function() {
            $(this).stop().animate({
                "opacity" : "0"
            }, "slow");
        }, function() {
            $(this).stop().animate({
                "opacity" : "1"
            }, "slow");
        });
    };

    /**
     * Regel: Das Fenster "Bitte starten sie die Suche ..." wird nur gezeigt,
     * wenn mindestens 5 Zeilen mit Leerzeichen eingeblendet werden.
     */
    $.fn.switchFoundHint = function() {

        var frstColText = $('table tbody tr th').text();
        var pattern = /\s{5,}/i;
        var tableEmpty = frstColText.match(pattern);
        // console.log('check: \'' + frstColText.match(pattern) + '\'');
        if (tableEmpty) {
            $('.tab-panel form.search .foundhint').css('display', 'inherit');
        } else {
            $('.tab-panel form.search .foundhint').css('display', 'none');
        }
    };

    /**
     * Regel: Wenn keine Tabellenzeilen eingeblendet sind, gibts ein
     * Hinweisfenster, dass die Suche nicht erfolgreich war.
     */
    $.fn.switchEmptySearch = function() {

        var frstColText = $('table tbody tr td').text();
        // console.log('check: ' + frstColText);
        if (!frstColText) {
            $('.tab-panel form.search .searchhint').show(700);
            $('.tab-panel form.search .pagination').css('display', 'none'); // Paginierung
                                                                            // ausblenden
        } else {
            $('.tab-panel form.search .searchhint').hide();
        }
    };

    /**
     * Entnommen aus:
     * https://cwiki.apache.org/confluence/display/WICKET/Generic+Busy+Indicator+%28for+both+Ajax+and+non-Ajax+submits%29
     * 
     * Wenn dieser Schritt wegfällt, haben Listener am Submit-Form Button keine
     * Wirkung! Also: $(formID).submit => keine Funktion. Warum dass so ist,
     * wurde leider nicht erklärt.
     */
    $.fn.setupWicketAjaxFunc = function() {

        // fuer die Entwicklung im Trockendock!
        if (typeof Wicket == 'undefined') {
            return;
        }

        // document.getElementsByTagName('body')[0].onclick = clickFunc;
        Wicket.Event.subscribe('/ajax/call/beforeSend', function(attributes, jqXHR, settings) {
            // $.fn.showFSBusy();
        });
        Wicket.Event.subscribe('/ajax/call/complete', function(attributes, jqXHR, textStatus) {
            $.fn.hideFSBusy();
        });
    };

    // function clickFunc(eventData) {
    // console.log('klickfunc');
    // var clickedElement = (window.event) ? event.srcElement :
    // eventData.target;
    // if ((clickedElement.tagName.toUpperCase() == 'BUTTON' ||
    // clickedElement.tagName.toUpperCase() == 'A' ||
    // clickedElement.parentNode.tagName.toUpperCase() == 'A'
    // || (clickedElement.tagName.toUpperCase() == 'INPUT' &&
    // (clickedElement.type.toUpperCase() == 'BUTTON' ||
    // clickedElement.type.toUpperCase() == 'SUBMIT')))
    // && clickedElement.parentNode.id.toUpperCase() != 'NOBUSY' ) {
    // showFSBusy();
    // }
    // };

    $.fn.showFSBusy = function() {
        $('.searchBusy').removeClass('invisible');
    };

    $.fn.hideFSBusy = function() {
        $('.searchBusy').addClass('invisible');
    };

    $.fn.showViewBusy = function() {
        // console.log("show view busy");
        $('.viewbusy').removeClass('invisible');
    };

    $.fn.hideViewBusy = function() {
        // console.log("hide view busy");
        $('.viewbusy').addClass('invisible');
    };

    $.fn.changeMaskStyle = function(style) {

        var myWindow = Wicket.Window.get();
        if (myWindow) {
            while (myWindow.oldWindow)
                myWindow = myWindow.oldWindow;
            if (myWindow.mask) {
                if (myWindow.mask.element)
                    myWindow.mask.element.className = style;
            }
        }
    };

})(jQuery);

(function() {

    // ///////////////////////////////////////////////////////////////////////////////////////
    // /// Timer
    // /

    var idleTime = 10;
    var idleInterval;
    var timeFormatet = 0;

    $.fn.startTimer = function(init) {
        clearInterval(idleInterval);
        idleInterval = setInterval("$.fn.countDownTimer()", 1000);
        idleTime = init;
    };

    $.fn.countDownTimer = function() {
        idleTime = idleTime - 1;

        if (idleTime > 60) {
            timeFormatet = "" + (idleTime / 60).toFixed(0) + " Min.";
        } else {
            timeFormatet = "" + idleTime.toFixed(0) + " Sek.";
        }
        $('#zeit').text(timeFormatet);

        if (idleTime <= 1) { // 1 Sekunden vorher, damit das DB-Update noch
                                // funzt.
            // window.location = 'logout.php'
            document.location.href = $('#logout').attr('href');
            clearInterval(idleInterval); // stop
            return;
        }
    };
})();
