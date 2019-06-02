(function (window, undefined) {

    var share_agree = document.getElementById("share_agree");
    var share_refuse = document.getElementById("share_refuse");

    share_agree.onclick = function (oEvent) {
        var that = this;
        var oEl = $(oEvent.currentTarget);
        var sId = $.trim(oEl.attr('data-id'));
        if (that.actioning) {
            return;
        }
        that.actioning = true;
        agree({
            newsId: sId,
            error: function () {
                alert('出现错误，请重试');
            },
            always: function () {
                that.actioning = false;
            }
        });
    };

    share_refuse.onclick = function (oEvent) {
        var that = this;
        var oEl = $(oEvent.currentTarget);
        var sId = $.trim(oEl.attr('data-id'));
        if (that.actioning) {
            return;
        }
        that.actioning = true;
        refuse({
            newsId: sId,
            error: function () {
                alert('出现错误，请重试');
            },
            always: function () {
                that.actioning = false;
            }
        });
    };


    function agree(oConf) {
        var that = this;
        fPost({
            url: '/manage/agree',
            data: {newsId: oConf.newsId},
            call: oConf.call,
            error: oConf.error,
            always: oConf.always
        });
    }

    function refuse(oConf) {
        var that = this;
        fPost({
            url: '/manage/refuse',
            data: {newsId: oConf.newsId},
            call: oConf.call,
            error: oConf.error,
            always: oConf.always
        });
    }

    function fPost(oConf) {
        var that = this;
        $.ajax({
            method: oConf.method || 'POST',
            url: oConf.url,
            dataType: 'json',
            data: oConf.data
        }).done(function (oResult) {
            var nCode = oResult.code;
            nCode === 0 && oConf.call && oConf.call(oResult);
            nCode !== 0 && oConf.error && oConf.error(oResult);
        }).fail(oConf.error).always(oConf.always);
    }

})(window);