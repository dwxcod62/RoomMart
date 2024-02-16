(function(FrontEnd) {
    (function(Product) {
        /**
         * 
         * @param {{
         *  productCookieName: string,
         *  productCookieValue: string,
         *  contextValue: {
         *      type: number,
         *      cate: number,
         *      city: string,
         *      distr: number,
         *      wardid: number,
         *      streetid: number,
         *      projid: number,
         *      priceLevel: number,
         *      areaLevel: number,
         *      direction: number,
         *      room: number
         *  }
         * }} params params
         */
        Product.DetailsBinnova = function(params) {

            (function constructor() {
                // set product cookie
                if (params.productCookieName && params.productCookieValue) {
                    setCookie(params.productCookieName, params.productCookieValue, null, '/');
                }

                $(window).resize(function() {
                    if ($(window).width() <= 1024) {
                        $(".footer-infomation").css("margin-bottom", "45px");
                    } else {
                        $(".footer-infomation").removeAttr("style");
                    }
                });

                if (localStorage) {
                    var local = JSON.parse(localStorage.getItem('USER_LOCATION'));

                    local = $.extend(local, params.contextValue);

                    localStorage.setItem('USER_LOCATION', JSON.stringify(local));
                }
                eraseCookie('PRODUCT_FILTER', '/', null);
            })();
        };
    })(FrontEnd.Product || (FrontEnd.Product = {}));
})(window.FrontEnd || (window.FrontEnd = {}));