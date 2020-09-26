;This file will be executed next to the application bundle image
;I.e. current directory will contain folder SX3ConfigurationUtility with application files
[Setup]
AppId={{fxApplication}}
AppName=SX3ConfigurationUtility
AppVersion=1.0
AppVerName=SX3ConfigurationUtility 1.0
AppPublisher=ANCIT Consulting
AppComments=SX3Configuration
AppCopyright=Copyright (C) 2020
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={localappdata}\SX3ConfigurationUtility
DisableStartupPrompt=Yes
DisableDirPage=No
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=ANCIT Consulting
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=SX3ConfigurationUtility1.0_21072020
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=SX3ConfigurationUtility\SX3ConfigurationUtility.ico
UninstallDisplayIcon={app}\SX3ConfigurationUtility.ico
UninstallDisplayName=SX3ConfigurationUtility
WizardImageStretch=No
WizardSmallImageFile=SX3ConfigurationUtility-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "SX3ConfigurationUtility\SX3ConfigurationUtility.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "SX3ConfigurationUtility\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\SX3ConfigurationUtility"; Filename: "{app}\SX3ConfigurationUtility.exe"; IconFilename: "{app}\SX3ConfigurationUtility.ico"; Check: returnTrue()
Name: "{commondesktop}\SX3ConfigurationUtility"; Filename: "{app}\SX3ConfigurationUtility.exe";  IconFilename: "{app}\SX3ConfigurationUtility.ico"; Check: returnFalse()


[Run]
Filename: "{app}\SX3ConfigurationUtility.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\SX3ConfigurationUtility.exe"; Description: "{cm:LaunchProgram,SX3ConfigurationUtility}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\SX3ConfigurationUtility.exe"; Parameters: "-install -svcName ""SX3ConfigurationUtility"" -svcDesc ""SX3ConfigurationUtility"" -mainExe ""SX3ConfigurationUtility.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\SX3ConfigurationUtility.exe "; Parameters: "-uninstall -svcName SX3ConfigurationUtility -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
