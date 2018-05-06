<#--
区域设备路径支持的 js template

@author gaohui
@date 2012-12-29
-->

<script id="nodeTemplate" type="text/template">
    <span class="m-l-5 il-blk">
        <div class="btn-group btn-group-sm" role="group">
            <% if(url !== undefined){ %>
            <a href="<%= url %>" class="btn btn-secondary btn-sm" style="border-radius:0;"><%= name %></a>
            <% }else { %>
            <button class="btn btn-secondary btn-sm" style="border-radius:0;"><%= name %></button>
            <% } %>
            <button class="btn btn-secondary dropdown-toggle btn-sm" data-toggle="dropdown" aria-haspopup="true"
                    aria-expanded="false">
                <span class="sr-only">Toggle Dropdown</span>
            </button>
            <div class="dropdown-menu">
                <% var i = 0; %>
                <% for(i = 0; i< children.length; i++) { %>
                <% if (children[i].open){ %>
                <a class="dropdown-item active" href="#">
                    <% if (children[i].type === 'zone'){ %>
                    <i class="icon-home"></i>
                    <% }else if(children[i].type === 'device'){ %>
                    <i class="icon-hdd"></i>
                    <% } %>

                    <%= children[i].name %>
                </a>
                <% }else{ %>
                <a class="dropdown-item" href="#">
                    <% if (children[i].type === 'zone'){ %>
                    <i class="icon-home"></i>
                    <% }else if(children[i].type === 'device'){ %>
                    <i class="icon-hdd"></i>
                    <% } %>

                    <%= children[i].name %>
                </a>
                <% }%>
                <% } %>
            </div>
        </div>
    </span>
</script>

<script id="singleNodeTemplate" type="text/template">
    <span class="m-l-10 il-blk">
        <span class="label label-default yahei-font"
              style="padding-top: 8px; padding-bottom: 8px; margin-top: 2px; font-weight: normal; font-size: 14px; text-shadow:none;">
              <%= name %>
        </span>
    </span>

</script>
