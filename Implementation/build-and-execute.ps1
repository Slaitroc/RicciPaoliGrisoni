# PowerShell script to manage Maven, Spring profiles, and Docker Compose

# Step 1: Set the paths
$MavenExecutable = "mvn"  # Ensure 'mvn' is in your PATH or specify the full path
$DockerComposeFile = "docker-compose.yml"  # Path to the Docker Compose file
$MavenProjectPath = Join-Path (Get-Location) "sc_server\"  # Update with relative path to pom.xml
$MavenAuthProjectPath = Join-Path (Get-Location) "sc_auth\"  # Update with relative path to pom.xml
$PropertiesFile = Join-Path $MavenProjectPath "src\main\resources\application.properties"

# Step 2: Prompt user to select the profile
Write-Host "Select the profile:" -ForegroundColor Yellow
Write-Host "[Press Enter for 'dev' or type 'p' for 'prod']"
$profileChoice = Read-Host "Enter your choice"

# Default values
$profile = "dev"
$services = "database-service"

if ($profileChoice -eq "p") {
    $profile = "prod"
    $services = ""  # For 'prod', run all services
}

Write-Host "Selected profile: $profile" -ForegroundColor Cyan

# Step 3: Update the application.properties file
Write-Host "Updating application.properties with profile: $profile..." -ForegroundColor Green
Set-Content -Path $PropertiesFile -Value "spring.profiles.active=$profile"
Write-Host "application.properties updated successfully." -ForegroundColor Green

# Step 4: Stop existing containers
Write-Host "Stopping existing Docker Compose services..." -ForegroundColor Yellow
Start-Process -FilePath "docker-compose" -ArgumentList "down" -NoNewWindow -Wait

# Step 5: Handle 'dev' profile
if ($profile -eq "dev") {
    Write-Host "Development profile selected." -ForegroundColor Cyan
    $rebuildChoice = Read-Host "Do you want to rebuild the database container before starting? (y/N)"
    $buildOption = ""
    if ($rebuildChoice -eq "y") {
        $buildOption = "--build"
    }
    Write-Host "Starting only the database container..." -ForegroundColor Green
    Start-Process -FilePath "docker-compose" -ArgumentList "up $services $buildOption -d" -NoNewWindow -Wait
    Write-Host "Database container started successfully." -ForegroundColor Green
    Write-Host "You can now start Spring Boot directly from your IDE." -ForegroundColor Cyan
    exit 0
}

# Step 6: Handle 'prod' profile
Write-Host "Production profile selected." -ForegroundColor Cyan

# Always rebuild for production
Write-Host "Rebuilding containers for production..." -ForegroundColor Green
Write-Host "Starting Maven build for production profile..." -ForegroundColor Green
$MavenBuildProcess = Start-Process -FilePath $MavenExecutable -ArgumentList "-f $MavenProjectPath\pom.xml clean package -Dmaven.test.skip=true" -NoNewWindow -Wait -PassThru

if ($MavenBuildProcess.ExitCode -ne 0) {
    Write-Error "sc_server --> Maven build failed. Check your project configuration and try again."
    exit $MavenBuildProcess.ExitCode
}

Write-Host "sc_server --> Maven build completed successfully." -ForegroundColor Green

$MavenBuildProcess = Start-Process -FilePath $MavenExecutable -ArgumentList "-f $MavenAuthProjectPath\pom.xml clean package -Dmaven.test.skip=true" -NoNewWindow -Wait -PassThru

if ($MavenBuildProcess.ExitCode -ne 0) {
    Write-Error "sc_auth --> Maven build failed. Check your project configuration and try again."
    exit $MavenBuildProcess.ExitCode
}

Write-Host "sc_auth --> Maven build completed successfully." -ForegroundColor Green


# Start all services with rebuild
Write-Host "Starting all services with Docker Compose and rebuilding..." -ForegroundColor Green
Start-Process -FilePath "docker-compose" -ArgumentList "up --build -d" -NoNewWindow -Wait
Write-Host "All services started successfully for production." -ForegroundColor Cyan
