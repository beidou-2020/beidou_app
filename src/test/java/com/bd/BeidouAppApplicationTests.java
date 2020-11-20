package com.bd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class BeidouAppApplicationTests {

  public static void main(String[] args) {
      String s = "abcdefj,";
      String substring = s.substring(0, s.length() - 1);
      log.info("str：{}", substring);
  }
}
