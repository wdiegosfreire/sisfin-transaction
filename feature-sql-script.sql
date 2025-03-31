-- Table STP_STATEMENT_PATTERN ----------
alter table sisfin_transaction.stp_statement_pattern add acc_identity_source int not null after stp_description;

update sisfin_transaction.stp_statement_pattern set acc_identity_source = 5 where acc_identity_source = 0;
commit;

alter table sisfin_transaction.stp_statement_pattern add constraint fk_acc_account_source_stp foreign key(acc_identity_source) references sisfin_transaction.acc_account(acc_identity);


-- Table STP_STATEMENT_PATTERN ----------
alter table sisfin_transaction.stp_statement_pattern add pam_identity int not null after loc_identity;

update sisfin_transaction.stp_statement_pattern set pam_identity = 2 where pam_identity = 0;
commit;

alter table sisfin_transaction.stp_statement_pattern add constraint fk_pam_payment_method_stp foreign key(pam_identity) references sisfin_transaction.pam_payment_method(pam_identity);


-- Table STP_STATEMENT_PATTERN ----------
alter table sisfin_transaction.stp_statement_pattern add stt_identity int not null after pam_identity;

update sisfin_transaction.stp_statement_pattern set stt_identity = 1 where stt_identity = 0;
commit;

alter table sisfin_transaction.stp_statement_pattern add constraint fk_stt_statement_type_stp foreign key(stt_identity) references sisfin_transaction.stt_statement_type(stt_identity);