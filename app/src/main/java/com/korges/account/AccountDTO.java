package com.korges.account;

import java.util.UUID;

record AccountDTO(UUID id, String name, String email, String password, String createdAt) {
}
