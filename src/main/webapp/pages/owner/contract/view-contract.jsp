<%--
  Created by IntelliJ IDEA.
  User: Kudamii
  Date: 2/13/2024
  Time: 12:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Contract</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <link rel="stylesheet" href="./assets/sys-css/contract/contract-details.css">
</head>
<body style="overflow-x: hidden">
    <div class="popup" id="popup">
        <div class="row">
            <div class="col-md-12">
                <canvas id="sig-canvas" width="400" height="400"> Get a better browser, bro. </canvas>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 mt-2">
                <button class="btn btn-primary me-2 mb-2" id="sig-submitBtn">Submit</button>
                <button class="btn btn-secondary me-2 mb-2" id="sig-clearBtn">Clear</button>
                <button class="btn btn-danger mb-2" id="sig-cancelBtn">Close</button>
            </div>
        </div>
    </div>

    <div class="card">
        <div class="row justify-content-center">
            <div class="col-12 col-sm-10">
                <div class="p-2 border shadow-hover">
                    <div>
                        <p style="margin: 0pt; text-align: right"><span style="font-family: 'Times New Roman'; font-size: 10pt">&nbsp;</span></p>

                        <h3 class="h3-text-2">
                            <span class="span-text bold">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</span>
                        </h3>

                        <h3 class="h3-text-2">
                            <span class="span-text bold">Độc lập – Tự do – Hạnh phúc</span>
                        </h3>

                        <h3 class="h3-text-2">
                            <span class="span-text bold">----------- o0o -----------</span>
                        </h3>

                        <h3 class="h3-text-2">
                            <span class="span-text bold">HỢP ĐỒNG THUÊ NHÀ</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Căn cứ vào bộ luật dân&nbsp;sự 2015.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Căn cứ vào luật thương mại năm 2005</span><span class="span-text">.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Căn cứ vào nhu cầu khả năng của hai bên.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">Hôm nay, ${requestScope.current_day} Chúng tôi gồm có:</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">BÊN CHO THUÊ (Gọi tắt là bên A)</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text">Ông: </span
                                    ><span class="span-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span
                        ><span class="span-text">${requestScope.OWNER_INFO.getFullname()}</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">CMND số: ${requestScope.OWNER_INFO.getCccd()}; Số điện thoại : ${requestScope.OWNER_INFO.getPhone()}</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text">HKTT: </span
                                    ><span class="span-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
                            <span class="span-text"> ${requestScope.OWNER_INFO.getAddress()} </span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text">Là chủ sở hữu và sử dụng hợp pháp của căn nhà cho thuê nêu tại Điều 1 hợp đồng này</span
                                    ><span class="span-text">.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">BÊN THUÊ (Gọi tắt là bên B)</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text">Ông/Bà: ${requestScope.RENTER_INFO.getFullname()} Sinh năm: ${requestScope.RENTER_INFO.getBirthday()}</span
                                    ><span class="span-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">CMND số: ${requestScope.RENTER_INFO.getCccd()}; Số điện thoại : ${requestScope.RENTER_INFO.getPhone()}</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >Bên A đồng ý cho Bên B thuê quyền sử dụng đất và tài sản gắn liền với đất để ở và kinh doanh hợp pháp theo các
                                        thỏa thuận sau:</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 1: NHÀ CHO THUÊ </span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Bên A đồng ý cho Bên B thuê phòng số ${sessionScope.room.getRoomNumber()} thuộc khu ${sessionScope.hostel.getHostelName()} theo Giấy chứng nhận quyền sở hữu nhà ở và quyền sử dụng đất</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Vị trí và diện tích thuê: ${sessionScope.hostel.getAddress()} , ${sessionScope.room.getRoomArea()} m <sup>2</sup> </span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 2: THỜI HẠN CHO THUÊ VÀ TIỀN ĐẶT CỌC</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >2.1 Thời hạn cho thuê&nbsp; nhà nêu tại điều 1 của hợp đồng này là: ${requestScope.between}, kể từ ${requestScope.room_startdate} đến
                                        ${requestScope.room_enddate}</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >2.2 Bên B đặt cọc cho bên A một khoản tiền là: ${requestScope.room_deposit} khoản tiền đặt cọc này được bên A trả lại cho bên B khi hết
                                        thời hạn hợp đồng này.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 3: GIÁ THUÊ VÀ PHƯƠNG THỨC THANH TOÁN</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Giá thuê nhà tại điều 1 của hợp đồng này như
                                        sau:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Giá thuê nhà hàng tháng là ${requestScope.room_fee} /01 tháng </span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Bên B thanh toán cho Bên A theo định kỳ ${requestScope.payment_term} tháng/lần. Và sẽ được thực hiện trong suốt thời hạn cho thuê.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Tiền thuế cho thuê nhà theo quy định của pháp luật, khoản thuế này do bên [] trả.</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Giá thuê nhà chưa bao gồm các chi phí sử dụng như: tiền điện, nước, điện thoại, internet, vv… các chi phí này
                                        sẽ được bên B( bên thuê nhà) trả riêng, theo mức tiêu thụ thực tế.</span
                                    >
                        </h3>


                        <%--                        <c:choose>--%>
                        <%--                            <c:when test="${false}">--%>
                        <%--                                <<h3 class="h3-text">--%>
                        <%--                                <span class="span-text"--%>
                        <%--                                >- Trong [] năm đầu tiên, từ ngày [] đến hết ngày [], tiền nhà cố định hàng tháng là []. /01 tháng--%>
                        <%--                                </span>--%>
                        <%--                                </h3>--%>

                        <%--                                <h3 class="h3-text">--%>
                        <%--                                <span class="span-text"--%>
                        <%--                                >- Giá thuê nhà trên được giữ ổn định trong []. năm đầu tiên của hợp đồng, từ năm thứ [] trở đi giá thuê nhà sẽ--%>
                        <%--                                    tăng không quá [].%.</span--%>
                        <%--                                >--%>
                        <%--                                </h3>--%>
                        <%--                            </c:when>--%>
                        <%--                        </c:choose>--%>


                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Các kỳ thanh toán tiếp theo chậm nhất sẽ là 05 ngày đầu kỳ của kỳ thanh toán tiếp theo.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 4: NGHĨA VỤ VÀ QUYỀN LỢI CỦA BÊN A.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">* Nghĩa vụ của bên A:</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Bên A giao căn nhà xây trên đất nêu tại điều 1 của hợp đồng này cho bên B vào ngày bắt đầu hợp đồng, việc bàn
                                        giao này sẽ lập thành biên bản, có xác nhận của đại diện mỗi bên và đảm bảo căn nhà phải thuộc quyền sở hữu hợp
                                        pháp của bên A.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Thanh toán toàn bộ tiền điện nước, tiền điện thoại, phí vệ sinh, an ninh trật tự và các khoản nghĩa vụ tài
                                        chính khác (nếu có) cho toàn bộ thời gian trước khi bàn giao căn nhà cho bên B.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Tạo mọi điều kiện để bên B được sử dụng căn nhà thuận tiện, không làm ảnh hưởng đến hoạt động kinh doanh của
                                        bên B, hỗ trợ bên B các thủ tục pháp lý liên quan đến việc sử dụng căn nhà (nếu có).</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">* Bên&nbsp; A có các quyền sau đây:</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Có quyền yêu cầu chấm dứt hợp đồng khi bên B sử dụng nhà không đúng mục đích, buôn bán, hàng quốc cấm và bị cơ
                                        quan nhà nước có thẩm quyền tước giấy phép kinh doanh.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Có quyền yêu cầu bên B thanh toán đúng hạn.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Yêu cầu bên B trả lại nhà khi hợp đồng chấm dứt hoặc khi thời hạn cho thuê đã hết.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 5: NGHĨA VỤ VÀ QUYỀN CỦA BÊN B.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">* Nghĩa vụ của bên B:</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Bên B tự bảo quản trang thiết bị mà bên A bàn giao trong quá trình sử dụng.</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text">- Sử dụng toàn bộ căn nhà vào mục đích kinh doanh buôn bán và vă</span
                                    ><span class="span-text">n phòng.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Trả đủ tiền thuê theo phương thức đã thỏa thuận.</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Tuân theo các quy định bảo vệ môi trường, không được làm tổn hại đến quyền, lợi ích của người sử dụng đất xung
                                        quanh, giữ gìn an ninh trật tự, phòng cháy chữa cháy, nếu xảy ra cháy nổ thì bên B phải hoàn toàn chịu trách nhiệm
                                        và phải bồi thường những khoản thiệt hại do lỗi của bên B gây ra.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Tự thanh toán tiền điện nước, tiền điện thoại, phí vệ sinh, an ninh trật tự và các khoản chi phí phát sinh
                                        trong quá trình sử dụng căn nhà kể từ ngày nhận bàn giao, thanh toán chi phí sửa chữa, thay mới các thiết bị hỏng
                                        nếu do lỗi bên B.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Khi bàn giao nhà phải đảm bảo các trang thiết bị được kê khai trong bảng kê tài sản lúc mới thuê đều trong tình
                                        trạng sử dụng tốt, không kể những hao mòn tự nhiên, hao mòn tất yếu do quá trình sử dụng trong giới hạn cho
                                        phép.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">* Bên B có các quyền sau đây:</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Yêu cầu bên A bàn giao nhà đúng như đã thỏa thuận.</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Lắp đặt các thiết bị, hệ thống dây điện thoại, hệ thống mạng và lắp đặt các thiết bị cần thiết phục vụ cho mục
                                        đích </span
                                    ><span class="span-text">kinh doanh.</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Được sử dụng toàn bộ phần diện tích vỉa hè dùng để xe của nhân viên và khách hàng; không gian mặt tiền để treo
                                        biển quảng cáo ... theo quy định của pháp luật.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Được quyền sửa chữa cải tạo&nbsp; căn nhà&nbsp; phù hợp với mục đích kinh doanh&nbsp; của bên B ( phải có sự
                                        đồng ý của Bên A bằng văn bản).</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 6: CHẤM DỨT HỢP ĐỒNG</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >6.1 Trong thời gian có hiệu lực của hợp đồng nếu một bên phát hiện phía bên kia có hành vi, vi phạm hợp đồng và
                                        có căn cứ về việc vi phạm đó thí phải thông báo bằng văn bản cho bên có hành vi vi phạm biết và yêu cầu khắc phục
                                        các vi phạm đó trong thời hạn 30 (ba mươi) ngày kể từ ngày nhận được thông báo mà bên có hành vi, vi phạm không
                                        khắc phục thì bên có quyền lợi bị vi phạm có quyền đơn phương chấm dứt hợp đồng trước và không phải bồi thường
                                        thiệt hại, bên vi phạm hợp đồng phải chịu trách nhiệm theo thời điểm xảy ra vi phạm phù hợp với quy định tại khoản
                                        6.2 điều này.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >6.2 Trong thời gian hợp đồng có hiệu lực, nếu một bên muốn chấm dứt hợp đồng trước thời hạn phải thông báo cho
                                        bên kia biết trước ít nhất 30 (ba mươi) ngày, các bên vẫn phải thực hiện nghĩa vụ của mình đến thời điểm chấm dứt,
                                        quyền lợi của các bên giải quyết như sau:</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Trường hợp bên A tự ý chấm dứt hợp đồng trước thời hạn:</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >+ Bên A tự ý chấm dứt hợp đồng sẽ bị phạt là 70 % giá trị các chi phí đầu tư cơ sở vật chất của bên B vào nhà cho
                                        thuê và trả cho Bên B một khoản tiền phạt vi phạm hợp đồng bằng 01 tháng tiền thuê nhà theo giá thuê đang thực
                                        hiện tại thời điểm ngay trước đó. Ngoài ra nếu Bên&nbsp; B đã trả trước tiền thuê nhà mà chưa được sử dụng thì bên
                                        A còn phải trả cho Bên B tiền thuê nhà của những tháng đã trả tiền thuê này.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Trường hợp Bên B tự ý chấm dứt hợp đồng trước thời hạn thì Bên B phải chịu&nbsp;&nbsp; mức phạt 01 tháng tiền
                                        thuê nhà.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Hợp đồng này được chấm dứt khi hết thời hạn cho thuê nêu tại điều 2.1 hợp đồng này.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 7: CAM ĐOAN CỦA CÁC BÊN.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">Bên A và bên B chịu trách nhiệm trước pháp luật về những lời cam đoan sau đây:</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">* Bên A cam đoan:</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Những thông tin về nhân thân, về căn nhà gắn liền với đất đã nêu tại Điều 1 trong hợp đồng này là đúng sự
                                        thật.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Tại thời điểm hai bên giao kết hợp đồng này căn nhà gắn liền với đất không có tranh chấp, không bị kê
                                        biên.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Việc giao kết hợp đồng này là hoàn toàn tự nguyện, không bị lừa dối, không bị ép buộc.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Thực hiện đúng và đầy đủ các thỏa thuận đã ghi trong hợp đồng này.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">* Bên B cam đoan:</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Những thông tin về nhân thân đã ghi trong hợp đồng này là đúng sự thật.</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Đã xem xét kỹ, biết rõ về căn nhà gắn liền với đất nêu tại điều 1 hợp đồng này và các giấy tờ về quyền sử dụng
                                        đất, quyền sở hữu tài sản gắn liền với đất.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Việc giao kết hợp đồng này là hoàn toàn tự nguyện, không bị lừa dối, không bị ép buộc.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Thực hiện đúng và đầy đủ các thỏa thuận đã ghi trong hợp đồng này.</span>
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text bold">ĐIỀU 8: ĐIỀU KHOẢN CHUNG</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Trong quá trình thực hiện hợp đồng này, nếu phát sinh tranh chấp, các bên cùng nhau thương lượng giải quyết
                                        trên nguyên tắc tôn trọng quyền lợi của nhau; trong trường hợp không thương lượng được thì một trong hai bên có
                                        quyền khởi kiện để yêu cầu tòa án có thẩm quyền giải quyết theo quy định của pháp luật.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Khi hợp đồng thuê nhà này hết hạn, nếu bên B có nhu cầu thuê tiếp thì bên A sẽ ưu tiên cho bên B được tiếp tục
                                        thuê trên cơ sở thỏa thuận của hai bên bằng một hợp đồng mới. Trong trường hợp này, hai bên sẽ thông báo cho nhau
                                        bằng văn bản trước ngày hợp đồng hết hạn 60 ngày. Giá thuê và những điều kiện cho thuê nhà sẽ được hai bên bàn bạc
                                        và thống nhất bằng một hợp đồng mới.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Ngoài các điều khoản đã nêu trong hợp đồng; các điều khoản khác được thực hiện theo quy định của pháp
                                        luật.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Hai bên đã hiểu rõ quyền, nghĩa vụ, lợi ích hợp pháp của mình, hậu quả pháp lý của việc giao kết hợp&nbsp; đồng
                                        và đã ký tên dưới đây để làm bằng chứng.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                            <span class="span-text">- Hợp đồng có hiệu lực kể từ ngày ký.</span>
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text"
                                    >- Kèm theo hợp đồng là Biên bản bàn giao nhà cho thuê. Biên bản bàn giao nhà cho thuê và các văn bản thoả thuận
                                        khác của hai bên trong quá trình thực hiện hợp đồng (nếu có) là một phần không thể tách rời của Hợp đồng
                                        này.</span
                                    >
                        </h3>

                        <h3 class="h3-text">
                                    <span class="span-text">- Hợp đồng này gồm 10 Điều, được lập thành 04 bản có </span
                                    ><span class="span-text">giá trị pháp lý như nhau; bên A giữ 02 bản, bên B giữ 02 bản.</span>
                        </h3>

                        <table cellpadding="0" cellspacing="0" style="width: 100%">
                            <tbody>
                            <tr>
                                <c:choose>
                                    <c:when test="${requestScope.contract_status eq 0}">
                                        <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                            <h3 class="h3-text-2">
                                                <span class="span-text bold">BÊN CHO THUÊ (BÊN A)</span>
                                                <div class="col-md-12">
                                                    <img  src="${requestScope.OWNER_SIGN}" alt="Your signature will go here!" class="alt-text" style="width: 50%; height: auto;"/>
                                                </div>
                                            </h3>
                                        </td>
                                        <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                            <h3 class="h3-text-2">
                                                <span class="span-text bold">BÊN THUÊ (BÊN B)</span>
                                                <div class="col-md-12">
                                                    <img  src="./assets/images/system/sign.jpg" alt="Your signature will go here!" class="alt-text" style="width: 50%; height: auto;"/>
                                                </div>
                                            </h3
                                        </td>
                                    </c:when>
                                    <c:when test="${true}">
                                        <c:choose>
                                            <c:when test="${sessionScope.USER.getRole() eq 1}">
                                                <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                                    <h3 class="h3-text-2">
                                                        <span class="span-text bold">BÊN CHO THUÊ (BÊN A)</span>
                                                        <div class="col-md-12">
                                                            <img  src="${requestScope.OWNER_SIGN}" alt="Your signature will go here!" class="alt-text" style="width: 50%; height: auto;"/>
                                                        </div>
                                                    </h3>
                                                </td>
                                                <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                                    <h3 class="h3-text-2">
                                                        <span class="span-text bold">BÊN THUÊ (BÊN B)</span>
                                                        <div class="col-md-12">
                                                            <img  src="./assets/images/system/sign.jpg" alt="Your signature will go here!" class="alt-text" style="width: 50%; height: auto;"/>
                                                        </div>
                                                    </h3>
                                                </td>
                                            </c:when>
                                            <c:when test="${true}">
                                                <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                                    <h3 class="h3-text-2">
                                                        <span class="span-text bold">BÊN CHO THUÊ (BÊN A)</span>
                                                        <div class="col-md-12">
                                                            <img id="sig-image" src="${requestScope.OWNER_SIGN}" alt="Click here to sign!" class="alt-text" style="width: 50%; height: auto;"/>
                                                        </div>
                                                    </h3>
                                                </td>
                                                <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                                    <h3 class="h3-text-2">
                                                        <span class="span-text bold">BÊN THUÊ (BÊN B)</span>
                                                        <div class="col-md-12">
                                                            <img src="./assets/images/system/sign.jpg" alt="Your signature will go here!" class="alt-text" style="width: 50%; height: auto;"/>
                                                        </div>
                                                    </h3>
                                                </td>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${requestScope.contract_status eq 2}">
                                        <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                            <h3 class="h3-text-2">
                                                <span class="span-text bold">BÊN CHO THUÊ (BÊN A)</span>
                                                <div class="col-md-12">
                                                    <img  src="${requestScope.OWNER_SIGN}" alt="Click here to sign!" class="alt-text" style="width: 50%; height: auto;"/>
                                                </div>
                                            </h3>
                                        </td>
                                        <td style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top; width: 225.1pt">
                                            <h3 class="h3-text-2">
                                                <span class="span-text bold">BÊN THUÊ (BÊN B)</span>
                                                <div class="col-md-12">
                                                    <img  src="${requestScope.RENTER_SIGN}" alt="Your signature will go here!" class="alt-text" style="width: 50%; height: auto;"/>
                                                </div>
                                            </h3
                                        </td>
                                    </c:when>
                                </c:choose>
                            </tr>
                            <tr>
                                <td colspan="2" style="padding-left: 5.4pt; padding-right: 5.4pt; vertical-align: top">
                                    <h3 class="h3-text-2">
                                        <span class="span-text bold">&nbsp;</span>
                                    </h3>

                                    <h3 class="h3-text-2">
                                        <span class="span-text bold">&nbsp;</span>
                                    </h3>

                                    <h3 class="h3-text-2">
                                        <span class="span-text bold">NGƯỜI LÀM CHỨNG</span>
                                    </h3>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="container my-5">
                    <div class="row">
                        <c:choose>
                            <c:when test="${requestScope.contract_status eq 0 || sessionScope.USER.getRole() eq 1 }">
                                <form action="sign" method="post" class="d-flex justify-content-between align-items-center w-100">
                                    <input id="asdfgh" name="asdfgh" style="display: none" value="">
                                    <button type="submit" class="btn btn-primary flex-grow-1">Gửi</button>
                                    <input  class="hidden flex-grow-1 mr-2" style="visibility: hidden;" ></input>
                                    <button type="button" class="btn btn-danger flex-grow-1 mr-2" >Hủy</button>
                                </form>
                            </c:when>
                            <c:when test="${true}">
                                <form action="createContract?action=delete" method="post" class="d-flex justify-content-between align-items-center w-100">
                                    <input id="asdfgh" name="asdfgh" style="display: none" value="">
                                    <input  class="hidden flex-grow-1 mr-2" style="visibility: hidden;" ></input>
                                    <input  class="hidden flex-grow-1 mr-2" style="visibility: hidden;" ></input>
                                    <button type="submit" class="btn btn-danger flex-grow-1 mr-2" >Hủy</button>
                                </form>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="./assets/js/contract/sign.js"></script>
</body>
</html>
