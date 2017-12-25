/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/dingjun/AndroidStudioProjects/Demo/app/src/main/aidl/com/eebbk/studyos/themes/IThemeAIDLInterface.aidl
 */
package com.eebbk.studyos.themes;
// Declare any non-default types here with import statements

public interface IThemeAIDLInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.eebbk.studyos.themes.IThemeAIDLInterface
{
private static final java.lang.String DESCRIPTOR = "com.eebbk.studyos.themes.IThemeAIDLInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.eebbk.studyos.themes.IThemeAIDLInterface interface,
 * generating a proxy if needed.
 */
public static com.eebbk.studyos.themes.IThemeAIDLInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.eebbk.studyos.themes.IThemeAIDLInterface))) {
return ((com.eebbk.studyos.themes.IThemeAIDLInterface)iin);
}
return new com.eebbk.studyos.themes.IThemeAIDLInterface.Stub.Proxy(obj);
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
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.eebbk.studyos.themes.IThemeAIDLCallback _arg0;
_arg0 = com.eebbk.studyos.themes.IThemeAIDLCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
com.eebbk.studyos.themes.IThemeAIDLCallback _arg0;
_arg0 = com.eebbk.studyos.themes.IThemeAIDLCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_updateThemeZip:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.updateThemeZip(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_updateKeyguardWallpaper:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.updateKeyguardWallpaper(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_updateWallpaper:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.updateWallpaper(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_updateRingtone:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.updateRingtone(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_updateFont:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.updateFont(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_resetFont:
{
data.enforceInterface(DESCRIPTOR);
this.resetFont();
reply.writeNoException();
return true;
}
case TRANSACTION_updateIcons:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.updateIcons(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_deleteIcons:
{
data.enforceInterface(DESCRIPTOR);
this.deleteIcons();
reply.writeNoException();
return true;
}
case TRANSACTION_updateKeyguardFromZK:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.updateKeyguardFromZK(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_deleteKeyguard:
{
data.enforceInterface(DESCRIPTOR);
this.deleteKeyguard();
reply.writeNoException();
return true;
}
case TRANSACTION_resetTheme:
{
data.enforceInterface(DESCRIPTOR);
this.resetTheme();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.eebbk.studyos.themes.IThemeAIDLInterface
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
@Override public void registerCallback(com.eebbk.studyos.themes.IThemeAIDLCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterCallback(com.eebbk.studyos.themes.IThemeAIDLCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * update a Zip file to Theme
    */
@Override public void updateThemeZip(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_updateThemeZip, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
    * change the Keyguard Wallpaper
    */
@Override public void updateKeyguardWallpaper(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_updateKeyguardWallpaper, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
    * change the Wallpaper
    */
@Override public void updateWallpaper(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_updateWallpaper, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
    * change the Ringtone
    */
@Override public void updateRingtone(java.lang.String path, int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_updateRingtone, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
    * change the fonts
    */
@Override public void updateFont(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_updateFont, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/*
    * reset the fonts
    */
@Override public void resetFont() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resetFont, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void updateIcons(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_updateIcons, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void deleteIcons() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_deleteIcons, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void updateKeyguardFromZK(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_updateKeyguardFromZK, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void deleteKeyguard() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_deleteKeyguard, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resetTheme() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resetTheme, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_updateThemeZip = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_updateKeyguardWallpaper = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_updateWallpaper = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_updateRingtone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_updateFont = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_resetFont = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_updateIcons = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_deleteIcons = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_updateKeyguardFromZK = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_deleteKeyguard = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_resetTheme = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
public void registerCallback(com.eebbk.studyos.themes.IThemeAIDLCallback cb) throws android.os.RemoteException;
public void unregisterCallback(com.eebbk.studyos.themes.IThemeAIDLCallback cb) throws android.os.RemoteException;
/**
    * update a Zip file to Theme
    */
public void updateThemeZip(java.lang.String path) throws android.os.RemoteException;
/*
    * change the Keyguard Wallpaper
    */
public void updateKeyguardWallpaper(java.lang.String path) throws android.os.RemoteException;
/*
    * change the Wallpaper
    */
public void updateWallpaper(java.lang.String path) throws android.os.RemoteException;
/*
    * change the Ringtone
    */
public void updateRingtone(java.lang.String path, int type) throws android.os.RemoteException;
/*
    * change the fonts
    */
public void updateFont(java.lang.String path) throws android.os.RemoteException;
/*
    * reset the fonts
    */
public void resetFont() throws android.os.RemoteException;
public void updateIcons(java.lang.String path) throws android.os.RemoteException;
public void deleteIcons() throws android.os.RemoteException;
public void updateKeyguardFromZK(java.lang.String path) throws android.os.RemoteException;
public void deleteKeyguard() throws android.os.RemoteException;
public void resetTheme() throws android.os.RemoteException;
}
