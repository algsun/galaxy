<div id="myCarousel" class="carousel slide" style="margin-bottom: 0">
    <div class="carousel-inner">
        <#if repairRecord.relic.photos??>
            <#list repairRecord.relic.photos as photoe>
                <div class="item <#if photoe_index==0>active</#if>" align="center">
                    <img src="${picturesBasePath}/${siteId}/${repairRecord.relic.id}/${photoe.path}" style="max-width: 350px;max-height: 350px;text-align: center"/>
                </div>
            </#list>
        </#if>
    </div>
    <!-- 轮播（Carousel）导航 -->
    <a class="carousel-control left" href="#myCarousel"
       data-slide="prev">&lsaquo;</a>
    <a class="carousel-control right" href="#myCarousel"
       data-slide="next">&rsaquo;</a>
</div>