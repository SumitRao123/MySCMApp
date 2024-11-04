# Smart Contract Manager Application

The Smart Contract Manager Application is a web-based platform designed to manage and store smart contracts securely and efficiently. This application is built with a range of modern technologies to provide a secure, user-friendly experience for handling contract data and related files.

## Features

- **User Registration and Account Verification**: Users can register, log in, and verify their accounts through email verification for enhanced security.
- **OAuth2 Authentication**: Secure authentication using OAuth2 allows users to sign in with their existing social accounts.
- **Image Storage with Cloudinary**: Contracts and user profiles can be associated with images that are securely stored in Cloudinary.
- **Contract Management**: Users can create, view, update, and delete contracts.
- **Role-Based Access Control**: Access and permissions are managed using Spring Security to ensure proper authorization.
- **Responsive Design**: A responsive UI built with Thymeleaf, HTML, CSS, and JavaScript provides an optimized experience across devices.

## Technologies Used

- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Backend**: Spring Boot, Spring Security, Spring Data JPA, Spring OAuth2
- **Database**: H2 (in-memory database for development), or any preferred relational database (e.g., MySQL, PostgreSQL) for production
- **Cloudinary**: For image storage and management
- **Authentication**: Spring Security and OAuth2 for secure login

## Getting Started

### Prerequisites

- Java 11+
- Maven or Gradle
- A Cloudinary account (for storing images)
- [Optional] A social login provider (Google, Facebook, etc.) for OAuth2

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SumitRao123/MySCMApp.git
   cd MySCMApp
