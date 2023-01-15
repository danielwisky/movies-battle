package br.com.danielwisky.moviesbattle.gateways.outputs.h2;

import static java.lang.String.format;

import br.com.danielwisky.moviesbattle.gateways.outputs.UserDataGateway;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.entities.UserEntity;
import br.com.danielwisky.moviesbattle.gateways.outputs.h2.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataH2Gateway implements UserDataGateway {

  private final UserEntityRepository userEntityRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    return userEntityRepository.findByUsername(username)
        .map(UserEntity::toDomain)
        .orElseThrow(() -> new UsernameNotFoundException(format("User not found with username: %s", username)));
  }
}