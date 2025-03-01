(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner(0);


    // Fixed Navbar
    $(window).scroll(function () {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', 0);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        }
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 1
            },
            992: {
                items: 2
            },
            1200: {
                items: 2
            }
        }
    });


    // vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 2
            },
            992: {
                items: 3
            },
            1200: {
                items: 4
            }
        }
    });


    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })
        //add active class to header
        const navElement = $("#navbarCollapse");
        const currentUrl = window.location.pathname;
        navElement.find('a.nav-link').each(function () {
            const link = $(this); // Get the current link in the loop
            const href = link.attr('href'); // Get the href attribute of the link

            if (href === currentUrl) {
                link.addClass('active'); // Add 'active' class if the href matches the current URL
            } else {
                link.removeClass('active'); // Remove 'active' class if the href does not match
            }
        });

    });



    // Product Quantity
    // $('.quantity button').on('click', function () {
    //     var button = $(this);
    //     var oldValue = button.parent().parent().find('input').val();
    //     if (button.hasClass('btn-plus')) {
    //         var newVal = parseFloat(oldValue) + 1;
    //     } else {
    //         if (oldValue > 0) {
    //             var newVal = parseFloat(oldValue) - 1;
    //         } else {
    //             newVal = 0;
    //         }
    //     }
    //     button.parent().parent().find('input').val(newVal);
    // });
    $('.quantity button').on('click', function () {
        var button = $(this);
        // Sử dụng closest để tìm phần tử input
        var input = button.closest('.quantity').find('input');
        var oldValue = parseFloat(input.val()) || 1;
        var newVal = oldValue;

        // Xác định số lượng mới
        if (button.hasClass('btn-plus')) {
            newVal = oldValue + 1;
        } else {
            if (oldValue > 1) {
                newVal = oldValue - 1;
            } else {
                newVal = 1;
            }
        }

        // Cập nhật số lượng
        input.val(newVal);

        //set form index
        const index = input.attr("data-cart-detail-index")
        const el = document.getElementById(`cartDetails${index}.quantity`);
        $(el).val(newVal);

        // Lấy giá và id
        var price = parseFloat(input.attr("data-cart-detail-price").replace(',', '')) || 0;
        var id = input.attr("data-cart-detail-id");

        // Cập nhật tổng tiền cho sản phẩm (chọn theo data-cart-total-id)
        var priceElement = $(`p[data-cart-total-id='${id}']`);
        if (priceElement.length > 0) {
            var newPrice = price * newVal;
            priceElement.text(formatCurrency(newPrice) + " đ");
        }

        // Cập nhật tổng tiền giỏ hàng
        updateCartTotal();
    });

    // Hàm tính tổng tiền giỏ hàng bằng cách duyệt qua từng dòng tổng sản phẩm
    function updateCartTotal() {
        var total = 0;
        // Giả sử mỗi dòng tổng tiền sản phẩm được đánh dấu bằng data-cart-total-id
        $("p[data-cart-total-id]").each(function () {
            // Loại bỏ các ký tự không phải số
            var itemTotal = parseFloat($(this).text().replace(/\D/g, "")) || 0;
            total += itemTotal;
        });

        // Cập nhật các phần tử hiển thị tổng giỏ hàng (ví dụ: có thuộc tính data-cart-total-price)
        $("p[data-cart-total-price]").each(function () {
            $(this).text(formatCurrency(total) + " đ");
            $(this).attr("data-cart-total-price", total);
        });
    }

    // Hàm định dạng tiền tệ
    function formatCurrency(value) {
        const formatter = new Intl.NumberFormat('vi-VN', {
            style: 'decimal',
            minimumFractionDigits: 0
        });
        let formatted = formatter.format(value);
        // Thay thế dấu chấm bằng dấu phẩy nếu cần
        formatted = formatted.replace(/\./g, ',');
        return formatted;
    }

    $('#btnFilter').click(function (event) {
        event.preventDefault();

        let factoryArr = [];
        let targetArr = [];
        let priceArr = [];
        //factory filter
        $("#factoryFilter .form-check-input:checked").each(function () {
            factoryArr.push($(this).val());
        });

        //target filter
        $("#targetFilter .form-check-input:checked").each(function () {
            targetArr.push($(this).val());
        });
        //price filter
        $("#priceFilter .form-check-input:checked").each(function () {
            priceArr.push($(this).val());
        });

        let sortValue = $('input[name="radio-sort"]:checked').val();

        const currentUrl = new URL(window.location.href);
        const searchParams = currentUrl.searchParams;

        // Add or update query parameters 
        searchParams.set('page', '1');
        searchParams.set('sort', sortValue);

        // reset khi khong co gia tri
        searchParams.delete('factory');
        searchParams.delete('target');
        searchParams.delete('price');

        if (factoryArr.length > 0) {
            searchParams.set('factory', factoryArr.join(','));
        }
        if (targetArr.length > 0) {
            searchParams.set('target', targetArr.join(','));
        }
        if (priceArr.length > 0) {
            searchParams.set('price', priceArr.join(','));
        }
        //update the URL and reload the Page
        window.location.href = currentUrl.toString();


    });


})(jQuery);

