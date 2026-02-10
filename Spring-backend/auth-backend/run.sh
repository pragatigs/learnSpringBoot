#!/bin/bash

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Configuration
JAR_FILE="build/libs/shopify-auth-backend-0.0.1-SNAPSHOT.jar"

echo -e "${GREEN}======================================${NC}"
echo -e "${GREEN}  Shopify Auth Service Dockerization${NC}"
echo -e "${GREEN}======================================${NC}"
echo ""

# Check if jar file exists
# if [ ! -f "$JAR_FILE" ]; then
#     echo -e "${YELLOW}JAR file not found. Building the application...${NC}"
#     echo ""
    
    # Build the project using Gradle
    if [ -f "./gradlew" ]; then
        chmod +x ./gradlew
        ./gradlew clean bootJar
    else
        echo -e "${RED}Error: gradlew not found!${NC}"
        exit 1
    fi
    
    # Check if build was successful
    if [ ! -f "$JAR_FILE" ]; then
        echo -e "${RED}Error: Build failed! JAR file was not created.${NC}"
        exit 1
    fi
    
    echo -e "${GREEN}Build completed successfully!${NC}"
    echo ""

    echo -e "${GREEN}JAR file found: $JAR_FILE${NC}"
    echo ""

# Stop any running containers
echo -e "${YELLOW}Stopping any running containers...${NC}"
docker-compose down

# Build and start Docker containers
echo -e "${YELLOW}Building and starting Docker containers...${NC}"
docker-compose up --build -d

# Check if containers are running
if [ $? -eq 0 ]; then
    echo ""
    echo -e "${YELLOW}Waiting for PostgreSQL to be ready...${NC}"
    
    # Wait for PostgreSQL to be healthy (max 30 seconds)
    for i in {1..30}; do
        if docker-compose exec -T postgres pg_isready -U postgres > /dev/null 2>&1; then
            echo -e "${GREEN}PostgreSQL is ready!${NC}"
            
            # Test database connection
            if docker-compose exec -T postgres psql -U postgres -d shopify_db -c "SELECT 1;" > /dev/null 2>&1; then
                echo -e "${GREEN}Database 'shopify_db' is accessible!${NC}"
            fi
            break
        fi
        sleep 1
    done
    
    echo ""
    echo -e "${GREEN}======================================${NC}"
    echo -e "${GREEN}  Application started successfully!${NC}"
    echo -e "${GREEN}======================================${NC}"
    echo ""
    echo -e "${GREEN}Application URL: ${NC}http://localhost:8080"
    echo -e "${GREEN}PostgreSQL: ${NC}localhost:5432"
    echo ""
    echo -e "${YELLOW}Useful commands:${NC}"
    echo -e "  View logs:           ${NC}docker-compose logs -f"
    echo -e "  View Postgres logs:  ${NC}docker-compose logs -f postgres"
    echo -e "  Connect to DB:       ${NC}docker-compose exec postgres psql -U postgres -d shopify_db"
    echo -e "  Stop containers:     ${NC}docker-compose down"
else
    echo -e "${RED}Error: Failed to start Docker containers!${NC}"
    exit 1
fi
