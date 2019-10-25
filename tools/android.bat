@echo off
setlocal enableDelayedExpansion

echo **************************************************************************
echo The "android" command is deprecated.
echo For manual SDK, AVD, and project management, please use Android Studio.
echo For command-line tools, use tools\bin\sdkmanager.bat
echo and tools\bin\avdmanager.bat
echo **************************************************************************
echo.

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.\

if not defined wrapper_bin_dir (
  set wrapper_bin_dir=bin
)

set avd_verbs=;list;create;move;delete;
set avd_objects=;avd;target;device;

call:checkMatch "%avd_verbs%" "%avd_objects%" %* || (
  call:invoke "%DIRNAME%%wrapper_bin_dir%\avdmanager" %* || exit /b 1
  exit /b 0
)

set sdk_verbs=;list;update;
set sdk_objects=;sdk;
call:checkMatch "%sdk_verbs%" "%sdk_objects%" %* || (
  call:runSdkCommand %* || exit /b 1
  exit /b 0
)

echo Invalid or unsupported command "%*"
echo.
echo Supported commands are:
echo android list target
echo android list avd
echo android list device
echo android create avd
echo android move avd
echo android delete avd
echo android list sdk
echo android update sdk
exit /b 1

:runSdkCommand
  setlocal enableDelayedExpansion
  set trysdk=""
  if defined USE_SDK_WRAPPER (set trysdk="%USE_SDK_WRAPPER%")
  call:findSdkParam %* || (
    set trysdk=y
  )
  if %trysdk%=="" (
    echo "android" SDK commands can be translated to sdkmanager commands on a best-effort basis.
    echo (This prompt can be suppressed with the --use-sdk-wrapper commend-line argument
    echo or by setting the USE_SDK_WRAPPER environment variable^)
    set /p trysdkresponse="Continue? [y/N]: "
    if /I "!trysdkresponse!"=="y" (
      set trysdk=y
    )
  )
  if %trysdk%=="" (
    echo Aborted
    exit /b 1
  )
  if "!verb!"=="list" (
    call:invoke "%DIRNAME%%wrapper_bin_dir%\sdkmanager" --list --verbose || exit /b 1
    exit /b 0
  )
  if "!verb!"=="update" (
    set args=
    set prev=
    set update_all=1
    call:sdkUpdate %* || exit /b 1
    exit /b 0
  )

:sdkUpdate
  setlocal enableDelayedExpansion
  set paramsWithArgument=;-p;--obsolete;-u;--no-ui;--proxy-host;--proxy-port;-t;--filter;
  set verb="%~1"
  set object="%~2"
  set args=
  set prev=""
  shift & shift
  :sdkupdateloop
    if "%~1"=="" (goto:sdkupdatedone)
    set arg="%~1"
    set unquotedarg=%~1
    shift
    if !arg!=="--use-sdk-wrapper" (
      goto:sdkupdateloop
    ) else if !arg!=="!verb!" (
      goto:sdkupdateloop
    ) else if !arg!=="!object!" (
      goto:sdkupdateloop
    ) else if !arg!=="-n" (
      echo "update sdk -n is not supported"
      exit /b 1
    ) else if !arg!=="-s" (
      set args=!args! --no_https
    ) else if !arg!=="--no-https" (
      set args=!args! --no_https
    ) else if !arg!=="-a" (
      set args=!args! --include_obsolete
    ) else if !arg!=="--all" (
      set args=!args! --include_obsolete
    ) else if "!paramsWithArgument:;%unquotedarg%;=!" neq "!paramsWithArgument!" (
      rem nothing
    ) else if "!arg:~1,1!"=="-" (
      echo Unrecognized argument !arg!
      exit /b 1
    ) else if !prev!=="--proxy-host" (
      set args=!args! --proxy=http --proxy_host=!unquotedarg!
    ) else if !prev!=="--proxy-port" (
      set args=!args! --proxy_port=!unquotedarg!
    ) else if !prev!=="-t" (
      set has_filter=y
      call:parseFilter !arg! || exit /b 1
      rem unquoted comma-separated lists are treated as separate args, so if
      rem the next arg isn't recognized as a flag, treat it as a filter element
      goto:sdkupdateloop
    ) else if !prev!=="--filter" (
      set has_filter=y
      call:parseFilter !arg! || exit /b 1
      rem unquoted comma-separated lists are treated as separate args, so if
      rem the next arg isn't recognized as a flag, treat it as a filter element
      goto:sdkupdateloop
    ) else (
      echo Unrecognized argument !arg!
      exit /b 1
    )
    set prev=!arg!
    goto:sdkupdateloop

  :sdkupdatedone
  if not defined has_filter (
    set args=%args% --update
  )
  call:invoke "%DIRNAME%%wrapper_bin_dir%\sdkmanager" %args% || exit /b 1
  exit /b 0

:parseFilter
  for %%i in (%~1) do (
    set filter=%%i
    if "!filter!"=="tool" (
      set args=!args! tools
    ) else if "!filter!"=="tools" (
      set args=!args! tools
    ) else if "!filter!"=="platform-tool" (
      set args=!args! platform-tools
    ) else if "!filter!"=="platform-tools" (
      set args=!args! platform-tools
    ) else if "!filter!"=="doc" (
      set args=!args! docs
    ) else if "!filter:~0,4!"=="lldb" (
      set args=!args! !filter:-=;!
    ) else if "!filter:~0,11!"=="build-tools" (
      set args=!args! !filter:build-tools-=build-tools;!
    ) else if "!filter!"=="ndk" (
      set args=!args! ndk-bundle
    ) else if "!filter:~0,8!"=="android-" (
      set args=!args! platforms;!filter!
    ) else if "!filter:~0,6!"=="extra-" (
      set tmp=!filter:extra-=extras-!
      set args=!args! !tmp:-=;!
    ) else (
      echo Filter !filter! is not supported
      exit /b 1
    )
  )
  exit /b 0


:findSdkParam
  :sdkloop
    if "%~1"=="" ( exit /b 0 )
    set arg=%~1
    shift

    if "%arg%"=="--use-sdk-wrapper" (
      exit /b 1
    )
    goto:sdkloop

:checkMatch
  set verbs=%~1
  set objects=%~2
  set verb=""
  set object=""
  shift & shift
  :loop
    if "%~1"=="" ( goto:done )
    set arg=%~1
    shift
    if "%arg:~0,1%"=="-" ( goto:loop )
    if !verb!=="" if "!verbs:;%arg%;=!" neq "!verbs!" (
      set verb=!arg!
      goto:loop
    )
    if !verb! neq "" if "!objects:;%arg%;=!" neq "!objects!" (
      set object=!arg!
      goto:done
    )

  :done
  if !verb! neq "" if !object! neq "" exit /b 1
  exit /b 0

:invoke
  echo Invoking %*
  echo.
  call %* || exit /b 1
  exit /b 0
