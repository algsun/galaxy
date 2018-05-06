<div ng-app="imageDatumApp" ng-controller='imageDatumCtrl'>
    <form class="form-horizontal" id="imageDatum_from">
        <input name="imageDatum.repairRecord.id" type="hidden" value="${repairRecordId!}"/>
        <div class="control-group">
            <label class="control-label">提交人</label>

            <div class="controls">
                <select name="imageDatum.submitPerson.id" id="">
                <#list users as user>
                    <option value="${user.id}">${user.userName}</option>
                </#list>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">内容表述</label>

            <div class="controls">
                <input type="text" name="imageDatum.content" id="content"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">介质</label>

            <div class="controls">
                <textarea id="media" name="imageDatum.media" cols="30" rows="3"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">时常</label>

            <div class="controls">
                <input type="text" id="duration" name="imageDatum.duration">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">采集时间</label>

            <div class="controls">
                <input type="text" id="image_datum_stamp" name="imageDatum.stamp" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})">
            </div>
        </div>
    </form>

    <form id="image_datum_upload_form" class="form-horizontal" enctype="multipart/form-data" action="image_datum_upload.action"
          method="post">
        <div class="control-group">
            <label class="control-label">上传文件</label>

            <div class="controls">
                <input type="hidden" id="image_datum_id" name="id" value=""/>
                <input type="file" name="image" id="image_datum_input" size="1">
                <span class="help-inline m-l-80 red" id="image_datum_input_help"></span>
            </div>
        </div>
    </form>

    <form class="form-horizontal">
        <div class="control-group">
            <label class="control-label"></label>

            <div class="controls">
                <button type="button" class="btn" id="image-datum-submit">保存</button>
            </div>
        </div>
    </form>
    <table border="1px" style="border:2px #000 solid;">
        <caption align="top"><h3>影像资料登记表</h3></caption>
        <tr>
            <td>编号</td>
            <td>提交人</td>
            <td>内容表述</td>
            <td>介质</td>
            <td>时长<span class="careful">(min)</span></td>
            <td>采集时间</td>
            <td>文件</td>
            <td>操作</td>
        </tr>
        <tr ng-repeat="i in imageDatums">
            <td>{{$index + 1}}</td>
            <td>{{i.submitPerson.userName}}</td>
            <td>{{i.content}}</td>
            <td>{{i.media}}</td>
            <td>{{i.duration}}</td>
            <td>{{i.stamp|date:'yyyy-MM-dd'}}</td>
            <td>
                <a ng-show="i.filePath" href="image_datum_downloadFile.action?fileName={{i.filePath}}" >点击下载</a>
                <span ng-show="i.filePath==null">无资源</span>
            </td>
            <td>
                <a href="archives/imageDatums/to_update.json?imageDatum.id={{i.id}}" class="btn btn-mini">修改</a>
                <a ng-click="imageDatumDeleteFun(i.id)" class="btn btn-mini">删除</a>
            </td>
        </tr>
    </table>
</div>