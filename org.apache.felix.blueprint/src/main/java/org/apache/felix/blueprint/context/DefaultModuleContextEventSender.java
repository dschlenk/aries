package org.apache.felix.blueprint.context;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Bundle;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.Event;
import org.osgi.service.blueprint.context.ModuleContextEventConstants;
import org.osgi.service.blueprint.context.ModuleContext;
import org.apache.felix.blueprint.ModuleContextEventSender;

/**
 * TODO: javadoc
 */
public class DefaultModuleContextEventSender implements ModuleContextEventSender {

    private final ServiceTracker eventAdminServiceTracker;

    public DefaultModuleContextEventSender(BundleContext bundleContext) {
        this.eventAdminServiceTracker = new ServiceTracker(bundleContext, EventAdmin.class.getName(), null);
        this.eventAdminServiceTracker.open();
    }

    public void sendCreating(ModuleContext moduleContext) {
        sendEvent(moduleContext, TOPIC_CREATING, null, null, null);
    }

    public void sendCreated(ModuleContext moduleContext) {
        sendEvent(moduleContext, TOPIC_CREATED, null, null, null);
    }

    public void sendDestroying(ModuleContext moduleContext) {
        sendEvent(moduleContext, TOPIC_DESTROYING, null, null, null);
    }

    public void sendDestroyed(ModuleContext moduleContext) {
        sendEvent(moduleContext, TOPIC_DESTROYED, null, null, null);
    }

    public void sendWaiting(ModuleContext moduleContext, String[] serviceObjectClass, String serviceFilter) {
        sendEvent(moduleContext, TOPIC_WAITING, null, serviceObjectClass, serviceFilter);
    }

    public void sendFailure(ModuleContext moduleContext, Throwable cause) {
        sendEvent(moduleContext, TOPIC_FAILURE, cause, null, null);
    }

    public void sendFailure(ModuleContext moduleContext, Throwable cause, String[] serviceObjectClass, String serviceFilter) {
        sendEvent(moduleContext, TOPIC_FAILURE, cause, serviceObjectClass, serviceFilter);
    }

    public void sendEvent(ModuleContext moduleContext, String topic, Throwable cause, String[] serviceObjectClass, String serviceFilter) {
        EventAdmin eventAdmin = getEventAdmin();
        if (eventAdmin == null) {
            return;
        }

        Bundle bundle = moduleContext.getBundleContext().getBundle();

        Dictionary props = new Hashtable();
        props.put("bundle.symbolicName", bundle.getSymbolicName());
        props.put("bundle.id", bundle.getBundleId());
        props.put("bundle", bundle);
        props.put("bundle.version", "NA");
        props.put("timestamp", System.currentTimeMillis());
        props.put(ModuleContextEventConstants.EXTENDER_BUNDLE, "NA");
        props.put(ModuleContextEventConstants.EXTENDER_ID, "NA");
        props.put(ModuleContextEventConstants.EXTENDER_SYMBOLICNAME, "NA");
        if (cause != null) {
            props.put("exception", cause);
        }
        if (serviceObjectClass != null) {
            props.put("service.objectClass", serviceObjectClass);
        }
        if (serviceFilter != null) {
            props.put("service.filter", serviceFilter);
        }

        Event event = new Event(topic, props);
        eventAdmin.postEvent(event);
        System.out.println("Event sent: " + topic);
    }

    private EventAdmin getEventAdmin() {
        return (EventAdmin)this.eventAdminServiceTracker.getService();
    }

    public void destroy() {
        this.eventAdminServiceTracker.close();
    }
}
