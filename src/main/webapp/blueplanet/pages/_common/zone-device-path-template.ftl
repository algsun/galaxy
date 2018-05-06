<#--
区域设备路径支持的 js template

@author gaohui
@date 2012-12-29
-->

<script id="nodeTemplate" type="text/template">
    <li class="m-l-5 il-blk">
        <div class="btn-group">
            <% if(url !== undefined){ %>
            <a href="<%= url %>" class="btn" style="border-radius:0;"><%= name %></a>
            <% }else { %>
            <button class="btn" style="border-radius:0;"><%= name %></button>
            <% } %>
            <button class="btn dropdown-toggle" style="border-radius:0;" data-toggle="dropdown">
                <span class="caret" style="border-top-color:#ccc; border-bottom-color:#ccc;"></span>
            </button>
            <ul class="dropdown-menu">
                <% var i = 0; %>
                <% for(i = 0; i< children.length; i++) { %>
                    <% if (children[i].open){ %>
                    <li class="active">
                    <% }else{ %>
                    <li>
                    <% }%>
                        <a href="#">
                            <% if (children[i].type === 'zone'){ %>
                                <i class="icon-home"></i>
                            <% }else if(children[i].type === 'device'){ %>
                                <i class="icon-hdd"></i>
                            <% } %>

                            <%= children[i].name %>
                        </a>
                    </li>
                <% } %>
            </ul>
        </div>
    </li>
</script>

<script id="singleNodeTemplate" type="text/tempalte">
    <li class="m-l-10 il-blk">
        <!--
        <button class="btn" style="border-radius:0;"><%= name %></button>
        -->
        <span class="label yahei-font"
              style="padding-top: 8px; padding-bottom: 8px; margin-top: 2px; font-weight: normal; font-size: 14px; text-shadow:none;"><%= name %></span>
    </li>
</script>
