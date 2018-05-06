package com.microwise.common.action;

import com.bastengao.struts2.freeroute.Results;
import com.bastengao.struts2.freeroute.annotation.Route;
import com.google.common.base.Strings;
import com.jcabi.manifests.Manifests;
import com.microwise.common.sys.annotation.Beans;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 应用状态
 *
 * @author gaohui
 * @date 13-12-10 10:21
 */
@Beans.Action("common:status")
public class StatusAction {
    public static final Logger log = LoggerFactory.getLogger(StatusAction.class);

    private String name = "galaxy";
    private String version;
    private int svn_revision;
    private long uptime;
    private String uptime_str;

    @Route("/status.json")
    public String status() {
        Date startTime = (Date) ServletActionContext.getServletContext().getAttribute("app.startTime");
        uptime = startTime.getTime();
        uptime_str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(uptime);
        initManifest();
        return Results.json().done();
    }

    private void initManifest(){
        try {
            Manifests.append(ServletActionContext.getServletContext());
            String svnRevision = Manifests.read("App-Svn-Revision");
            if(!Strings.isNullOrEmpty(svnRevision)){
                svn_revision = Integer.parseInt(svnRevision);
            }
            version = Manifests.read("App-Version");
        }catch (IllegalArgumentException e){
            // do nothing
            log.debug("", e);
        } catch (IOException e) {
            // do nothing
            log.debug("", e);
        }
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getSvn_revision() {
        return svn_revision;
    }

    public long getUptime() {
        return uptime;
    }

    public String getUptime_str() {
        return uptime_str;
    }
}
