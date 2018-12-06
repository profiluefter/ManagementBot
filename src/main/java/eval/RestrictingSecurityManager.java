package eval;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

public class RestrictingSecurityManager extends SecurityManager {
	private boolean isRestricted() {
		for (Class<?> cls : getClassContext())
			if (cls.getClassLoader() instanceof RestrictingClassLoader)
				return true;
		return false;
	}

	@Override
	public void checkCreateClassLoader() {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
			super.checkCreateClassLoader();
	}

	@Override
	public void checkExec(String cmd) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
			super.checkExec(cmd);
	}

	@Override
	public void checkExit(int status) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
			super.checkExit(status);
	}

	@Override
	public void checkLink(String lib) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
			super.checkLink(lib);
	}

	@Override
	public void checkPermission(Permission perm) {
		super.checkPermission(perm);
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
		super.checkPermission(perm, context);
	}

	@Override
	public void checkAccess(Thread t) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkAccess(t);
	}

	@Override
	public void checkAccess(ThreadGroup g) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkAccess(g);
	}

	@Override
	public void checkRead(FileDescriptor fd) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkRead(fd);
	}

	@Override
	public void checkRead(String file) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkRead(file);
	}

	@Override
	public void checkRead(String file, Object context) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkRead(file, context);
	}

	@Override
	public void checkWrite(FileDescriptor fd) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkWrite(fd);
	}

	@Override
	public void checkWrite(String file) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkWrite(file);
	}

	@Override
	public void checkDelete(String file) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkDelete(file);
	}

	@Override
	public void checkConnect(String host, int port) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkConnect(host, port);
	}

	@Override
	public void checkConnect(String host, int port, Object context) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkConnect(host, port, context);
	}

	@Override
	public void checkListen(int port) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkListen(port);
	}

	@Override
	public void checkAccept(String host, int port) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkAccept(host, port);
	}

	@Override
	public void checkMulticast(InetAddress maddr) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkMulticast(maddr);
	}

	@Override
	public void checkMulticast(InetAddress maddr, byte ttl) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkMulticast(maddr, ttl);
	}

	@Override
	public void checkPropertiesAccess() {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkPropertiesAccess();
	}

	@Override
	public void checkPropertyAccess(String key) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkPropertyAccess(key);
	}

	@Override
	public boolean checkTopLevelWindow(Object window) {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		return super.checkTopLevelWindow(window);
	}

	@Override
	public void checkPrintJobAccess() {
		if (isRestricted())
			throw new SecurityException("Not permitted!");
		else
		super.checkPrintJobAccess();
	}

	@Override
	public void checkSystemClipboardAccess() {
		super.checkSystemClipboardAccess();
	}

	@Override
	public void checkAwtEventQueueAccess() {
		super.checkAwtEventQueueAccess();
	}

	@Override
	public void checkPackageAccess(String pkg) {
		super.checkPackageAccess(pkg);
	}

	@Override
	public void checkPackageDefinition(String pkg) {
		super.checkPackageDefinition(pkg);
	}

	@Override
	public void checkSetFactory() {
		super.checkSetFactory();
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int which) {
		super.checkMemberAccess(clazz, which);
	}

	@Override
	public void checkSecurityAccess(String target) {
		super.checkSecurityAccess(target);
	}
}
