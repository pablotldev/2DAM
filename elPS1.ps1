#! Pegar en $PROFILE

oh-my-posh init pwsh --config 'https://raw.githubusercontent.com/JanDeDobbeleer/oh-my-posh/main/themes/zash.omp.json' | Invoke-Expression
Import-Module -Name Terminal-Icons

$global:Atajos = @{

  ad = "$HOME\Documents\2DAM\AccesoDatos"
  psp = "$HOME\Documents\2DAM\PSP"
  di = "$HOME\Documents\2DAM\DesarrolloInterfaces"
  dam = "$HOME\Documents\2DAM"
  g = "$HOME\Documents\gachamon"
  rl = "$HOME\Documents\rust_learn"

}

foreach ($k in $global:Atajos.Keys) {

  $ruta = $global:Atajos[$k]
  Set-Item "function:global:$k" { Set-Location $ruta }.GetNewClosure()
  
}

function c {
  param (
    [string]$destino = "."
  )
  if ($global:Atajos.ContainsKey($destino)) {
    $destino = $global:Atajos[$destino]
  }
  code $destino
  
}

Register-ArgumentCompleter -CommandName c -ParameterName destino -ScriptBlock {

  param($cmd, $param, $word) {
    $global:Atajos.Keys | Where-Object { $_ -like "$word" } | Sort-Object | ForEach-Object {

      [System.Management.Automation.CompletionResult]::new($_, $_, 'ParameterValue', $_)

    }
  }

} 

function p { g && c . && pnpm dev }
function m { mvn @args }
function mcc { m clean compile }
function mjr { m javafx:run }
function mdt { m dependency:tree }
function mt { m test }