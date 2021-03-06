/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/dingjun/AndroidStudioProjects/Demo/app/src/main/aidl/com/eebbk/studyos/themes/IThemeAIDLCallback.aidl
 */
package com.eebbk.studyos.themes;
// Declare any non-default types here with import statements

public interface IThemeAIDLCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.eebbk.studyos.themes.IThemeAIDLCallback
{
private static final java.lang.String DESCRIPTOR = "com.eebbk.studyos.themes.IThemeAIDLCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.eebbk.studyos.themes.IThemeAIDLCallback interface,
 * generating a proxy if needed.
 */
public static com.eebbk.studyos.themes.IThemeAIDLCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.eebbk.studyos.themes.IThemeAIDLCallback))) {
return ((com.eebbk.studyos.themes.IThemeAIDLCallback)iin);
}
return new com.eebbk.studyos.themes.IThemeAIDLCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onResult:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onResult(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.eebbk.studyos.themes.IThemeAIDLCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void onResult(int ret) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(ret);
mRemote.transact(Stub.TRANSACTION_onResult, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void onResult(int ret) throws android.os.RemoteException;
}
