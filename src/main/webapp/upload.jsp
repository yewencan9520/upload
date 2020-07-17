<%--
  Created by IntelliJ IDEA.
  User: yewencandePC
  Date: 2020/7/17
  Time: 17:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <base href="<%=request.getContextPath()%>/">
    <title>Title</title>
    <script src="js/jquery-3.1.1.js"></script>
</head>
<body>
<form id="images" action="save" method="post" enctype="multipart/form-data">
    <input type="text" name="username" placeholder="用户名"><br>
    <input id="imgPath" type="hidden" name="imgPath" value="">
    <input type="file" name="file" onchange="ups()"><br>
    <img id="showImg" src="" width="300px" height="150px"><br>
    <input type="submit" value="点击上传">

<script>
    function ups() {

        var form=$("#images")[0];
        var formData=new FormData(form)
        $.ajax({
            url:"upload",
            type:"post",
            data:formData,
            contentType:false,//不指定任何的数据类型，二进制传输
            processData:false,//不在传输过程中格式化数据，二进制传输
            success:function (data) {
                if (data.code== 0) {
                    $("#showImg").attr("src",data.msg);
                    $("#imgPath").val(data.msg);
                } else {
                    alert("上传失败");
                }
            }
        });
    }
</script>

</form>
</body>
</html>
