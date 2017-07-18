!function (t) {
    t.fn.values = function (e) {
        var i = t(this).find(":input").get();
        var jquery_values_check_data;
        var jquery_values_check_flag = false;

        return (e = {},
           t.each(i, function () {
                if(jquery_values_check_flag && !jquery_values_check_data){
                    jquery_values_check_flag = false;
                }else {
                    if(this.name &&
                       (this.checked
                           || /select|textarea/i.test(this.nodeName)
                           || /text|hidden|password|number/i.test(this.type))
                    ){
                        if(this.name !== "groupCheck"){
                            e[this.name] = t(this).val();
                        }
                    }
                }

                if(/checkbox/i.test(this.type) && /groupCheck/i.test(this.name)){
                    jquery_values_check_data = this.checked;
                    jquery_values_check_flag = true;
                }
            }
      ), e)
    }

    $("body").on("click", ".btn[modal='reset']", function() {
        $('select').chosen('destroy');
        $('select').prop("selectedIndex", -1);
        $('select').chosen();

        // $(".chosen-select").val('').trigger("chosen:updated");

        var ref;
        return (ref = $(this).parents("form")[0]) != null ? ref.reset() : void 0;
    });
}(jQuery);
