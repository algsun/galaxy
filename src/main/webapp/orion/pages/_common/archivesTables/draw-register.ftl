<div ng-controller='customersCtrl'>

    <form class="form-horizontal" id="drawRegister_form">
        <input name="drawRegister.repairRecord.id" type="hidden" value="${repairRecordId!}"/>

        <div class="control-group">
            <label class="control-label">图纸类型</label>

            <div class="controls">
                <input type="text" id="drawingType" name="drawRegister.drawingType">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">简单描述</label>

            <div class="controls">
                <input type="text" name="drawRegister.description" id="description"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">绘图人</label>

            <div class="controls">
                <select name="drawRegister.drawingPerson.id" id="">
                <#list users as user>
                    <option value="${user.id}">${user.userName}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">时间</label>

            <div class="controls">
                <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" id="stamp"
                       name="drawRegister.stamp">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注</label>

            <div class="controls">
                <input type="text" id="remark" name="drawRegister.remark">
            </div>
        </div>

    </form>

    <form id="uploadForm" class="form-horizontal" enctype="multipart/form-data" action="draw_upload.action"
          method="post">
        <div class="control-group">
            <label class="control-label">上传图纸</label>

            <div class="controls">
                <input type="hidden" id="draw_register_id" name="id" value=""/>
                <input type="file" name="image" id="imageInput" size="1">
                <span class="help-inline m-l-80 red" id="imageInput-help"></span>
            </div>
        </div>
    </form>

    <form class="form-horizontal">
        <div class="control-group">
            <label class="control-label"></label>

            <div class="controls">
                <button type="button" onclick="" class="btn" id="draw-register-submit">保存</button>
            </div>
        </div>
    </form>
    <table border="1px">
        <caption align="top"><h3>绘图登记表</h3></caption>
        <tr>
            <td>编号</td>
            <td>图纸类型</td>
            <td>简单描述</td>
            <td>绘图人</td>
            <td>时间</td>
            <td>备注</td>
            <td>下载</td>
            <td>操作</td>
        </tr>
        <tr ng-repeat="d in drawRegisters">
            <td>{{$index + 1}}</td>
            <td>{{d.drawingType}}</td>
            <td>{{d.description}}</td>
            <td>{{d.drawingPerson.userName}}</td>
            <td>{{d.stamp|date:'yyyy-MM-dd'}}</td>
            <td>{{d.remark}}</td>
            <td>
                <a ng-show="d.imgPath" href="draw_downloadFile.action?fileName={{d.imgPath}}" >点击下载</a>
                <span ng-show="d.imgPath==null">无资源</span>
            </td>
            <td>
                <a href="archives/drawRegister/to_update?drawRegister.id={{d.id}}" class="btn btn-mini">修改</a>
                <a ng-click="drawDeleteFun(d.id)" class="btn btn-mini">删除</a>
            </td>
        </tr>
    </table>


</div>
